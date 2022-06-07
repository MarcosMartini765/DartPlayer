package com.martini.dartplayer.services.playerService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.martini.dartplayer.R
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.dispatchers.player.PlayerAction
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.entity.player.PlaybackMode
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlayNextSongsParams
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.params.player.SetCachedPlaylistParams
import com.martini.dartplayer.domain.params.player.StartNewSessionPausedParams
import com.martini.dartplayer.domain.usecases.playerService.*
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsState
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsUseCase
import com.martini.dartplayer.domain.usecases.playerSettings.UpdatePlayerSettingsState
import com.martini.dartplayer.domain.usecases.playerSettings.UpdatePlayerSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.Duration
import javax.inject.Inject

@FlowPreview
@AndroidEntryPoint
class PlayerService : LifecycleService(), Player.Listener,
    PlayerNotificationManager.NotificationListener {

    @Inject
    lateinit var exoPlayer: ExoPlayer

    @Inject
    lateinit var eventsDispatcher: PlayerEventsDispatcher

    @Inject
    lateinit var mediaSession: MediaSessionCompat

    @Inject
    lateinit var clearPlaylistCacheUseCase: ClearPlaylistCacheUseCase

    @Inject
    lateinit var setCachedPlaylistUseCase: SetCachedPlaylistUseCase

    @Inject
    lateinit var getCachedPlaylistUseCase: GetCachedPlaylistUseCase

    @Inject
    lateinit var getCachedParametersUseCase: GetCachedParametersUseCase

    @Inject
    lateinit var setCachedParametersUseCase: SetCachedParametersUseCase

    @Inject
    lateinit var getPlayerSettingsUseCase: GetPlayerSettingsUseCase

    @Inject
    lateinit var updatePlayerSettingsUseCase: UpdatePlayerSettingsUseCase

    @Inject
    lateinit var playerMediaDescriptionAdapter: PlayerMediaDescriptionAdapter

    @Inject
    lateinit var playerDispatcher: PlayerDispatcher

    private lateinit var mediaSessionConnector: MediaSessionConnector

    private lateinit var playerNotificationManager: PlayerNotificationManager

    private val bluetoothReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                    if (state == BluetoothAdapter.STATE_TURNING_OFF) {
                        if (sessionActive) {
                            exoPlayer.pause()
                        }
                    }
                }
            }
        }
    }

    private var sessionActive = false

    private val defaultSettings = PlayerSettings(
        respectAudioFocus = true,
        playbackMode = PlaybackMode.Loop
    )

    private var timer: Job? = null

    private var playlistJob: Job? = null

    private val metadataBuilder = MediaMetadata.Builder()
    private val itemBuilder = MediaItem.Builder()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        setupMediaSession()
        initializePlayer()
        attachNotification()
        bluetoothListener()
        settingsListener()
        retrieveLastSession()
        listenToActions()
    }

    private fun listenToActions() {
        playerDispatcher.listen
            .onEach {
                when (it) {
                    PlayerAction.PlayerNext -> next()
                    PlayerAction.PlayerBack -> back()
                    PlayerAction.PlayerPlayOrPause -> pauseOrPlay()
                    is PlayerAction.PlayNextSongs -> playNextSongs(it.params)
                    is PlayerAction.PlayerRemoveSongsFromPlaylist -> removeSongsFromPlaylist(it.ids)
                    is PlayerAction.StartNewSession -> startNewSession(it.params)
                    is PlayerAction.SeekTo -> seekTo(it.position)
                    is PlayerAction.JumpTo -> jumpTo(it.index)
                    is PlayerAction.Reorder -> reorder(it.oldIndex, it.newIndex)
                    is PlayerAction.Shuffle -> shuffle(it.shuffle)
                    is PlayerAction.RepeatMode -> setRepeatMode(it.mode)
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun startPlayerService(
        notificationId: Int,
        notification: Notification,
    ) {
        startForeground(notificationId, notification)
        if (timer == null) {
            timer = durationTimer(Duration.ofSeconds(1))
        }
        dispatchSessionActive()
    }

    private fun stopServiceDoNotRemoveNot() {
        stopForeground(false)
        timer?.cancel()
        timer = null
    }

    private fun setupMediaSession() {
        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(exoPlayer)
    }

    private fun bluetoothListener() {
        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothReceiver, filter)
    }

    private fun convertPlaybackModeToExoPlayerMode(mode: PlaybackMode) {
        when (mode) {
            PlaybackMode.Loop -> {
                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
            }
            PlaybackMode.Repeat -> {
                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_OFF
            }
            PlaybackMode.OneTime -> {
                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
            }
        }
    }

    private fun settingsListener() {
        updatePlayerSettingsUseCase.listen
            .onEach {
                when (it) {
                    is UpdatePlayerSettingsState.Loaded -> {
                        exoPlayer.setAudioAttributes(
                            createAttributes(),
                            it.settings.respectAudioFocus
                        )
                        convertPlaybackModeToExoPlayerMode(it.settings.playbackMode)
                    }
                    is UpdatePlayerSettingsState.UpdateRepeatMode -> {
                        convertPlaybackModeToExoPlayerMode(it.settings.playbackMode)
                    }
                    else -> {

                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun activateSession() {

        if (sessionActive) return

        mediaSession.isActive = true

        dispatchSessionActive()
    }

    private fun pauseOnEnded() {
        eventsDispatcher.isPlaying(false)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun dispatchSessionActive() {

        if (sessionActive) return

        sessionActive = true

        eventsDispatcher.sessionChanged(sessionActive)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun disableSession() {
        sessionActive = false
        mediaSession.isActive = false

        timer?.cancel()
        timer = null

        eventsDispatcher.sessionChanged(sessionActive)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun initializePlayer() {
        getPlayerSettings()
        exoPlayer.addListener(this)
    }

    private fun getPlayerSettings() {
        getPlayerSettingsUseCase()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is GetPlayerSettingsState.Loaded -> {
                        exoPlayer.setAudioAttributes(
                            createAttributes(),
                            it.settings.respectAudioFocus
                        )
                        when (it.settings.playbackMode) {
                            PlaybackMode.Loop -> {
                                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
                            }
                            PlaybackMode.Repeat -> {
                                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_OFF
                            }
                            else -> {
                                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
                            }
                        }
                    }
                    is GetPlayerSettingsState.Failure -> {
                        exoPlayer.setAudioAttributes(
                            createAttributes(),
                            defaultSettings.respectAudioFocus
                        )
                        if (defaultSettings.playbackMode == PlaybackMode.Loop) {
                            exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
                        } else {
                            exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_OFF
                        }
                    }
                    else -> {

                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun createAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
    }

    private fun attachNotification() {
        playerNotificationManager = PlayerNotificationManager
            .Builder(
                this,
                Constants.PLAYER_SERVICE_NOT_ID,
                Constants.PLAYER_SERVICE_CHANNEL_ID
            )
            .setNotificationListener(this)
            .setMediaDescriptionAdapter(playerMediaDescriptionAdapter)
            .build()
        playerNotificationManager.setPlayer(exoPlayer)
    }

    private fun createNotificationChannel() = lifecycleScope.launch {

        val name = getString(R.string.PlayerServiceNotificationName)
        val descriptionText = getString(R.string.PlayerServiceNotificationDescription)
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(Constants.PLAYER_SERVICE_CHANNEL_ID, name, importance)
        mChannel.description = descriptionText

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val groupName = getString(R.string.PlayerServiceNotificationGroup)
        notificationManager.createNotificationChannelGroup(
            NotificationChannelGroup(
                Constants.PLAYER_NOTIFICATION_GROUP_ID,
                groupName
            )
        )
        mChannel.group = Constants.PLAYER_NOTIFICATION_GROUP_ID
        notificationManager.createNotificationChannel(mChannel)
    }

    private fun durationTimer(
        duration: Duration
    ): Job {
        return lifecycleScope.launch {
            while (isActive) {
                eventsDispatcher.position(getExoPlayerPosition())
                    .flowOn(Dispatchers.IO)
                    .launchIn(this)
                setCachedPlaylistParams()
                delay(duration.toMillis())
            }
        }
    }

    private fun getExoPlayerPosition(): Long {
        val position = exoPlayer.currentPosition
        return if (position < 0) {
            0
        } else {
            position
        }
    }

    private fun retrieveLastSession() {
        getCachedPlaylistUseCase()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is GetCachedPlaylistState.Loaded -> {
                        if (it.params.songs.isNotEmpty()) {
                            getCachedParametersUseCase()
                                .flowOn(Dispatchers.IO)
                                .onEach { state ->
                                    when (state) {
                                        is GetCachedParamsState.Loaded -> {
                                            startNewSessionPaused(
                                                StartNewSessionPausedParams(
                                                    position = state.params.position,
                                                    index = state.params.index.toInt(),
                                                    songs = it.params.songs,
                                                )
                                            )
                                        }
                                        else -> {}
                                    }
                                }
                                .launchIn(lifecycleScope)
                        }
                    }
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
        if (reason != ExoPlayer.TIMELINE_CHANGE_REASON_PLAYLIST_CHANGED) return
        cachePlaylist()
        super.onTimelineChanged(timeline, reason)
    }

    private fun cachePlaylist() {
        playlistJob?.cancel()
        playlistJob = null
        playlistJob = lifecycleScope.launch {
            delay(Constants.DEBOUNCE_TIME)
            setCachedPlaylistUseCase(
                SetCachedPlaylistParams(
                    timeline = exoPlayer.currentTimeline,
                    shuffled = exoPlayer.shuffleModeEnabled,
                    repeat = exoPlayer.repeatMode
                )
            )
                .flowOn(Dispatchers.IO)
                .launchIn(this)
        }
    }

    private fun setRepeatMode(mode: PlaybackMode) {
        val playerMode = when (mode) {
            PlaybackMode.OneTime -> ExoPlayer.REPEAT_MODE_ONE
            PlaybackMode.Loop -> ExoPlayer.REPEAT_MODE_ALL
            PlaybackMode.Repeat -> ExoPlayer.REPEAT_MODE_OFF
        }
        exoPlayer.repeatMode = playerMode
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        eventsDispatcher.shuffle(shuffleModeEnabled)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
        cachePlaylist()
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
    }

    private fun setCachedPlaylistParams() {
        setCachedParametersUseCase(
            CachedPlaybackParameters(
                index = exoPlayer.currentMediaItemIndex.toLong(),
                position = exoPlayer.currentPosition,
                shuffled = exoPlayer.shuffleModeEnabled
            )
        )
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun createMediaItemFromSong(song: Song): MediaItem {
        val artworkUri = song.imageUri?.let { Uri.parse(it) }
        val bundle = Bundle().also {
            it.putLong(Constants.EXTRA_BUNDLE_DURATION_KEY, song.duration)
        }
        val metadata = metadataBuilder
            .setArtist(song.artist)
            .setTitle(song.name)
            .setAlbumTitle(song.album)
            .setMediaUri(Uri.parse(song.uri))
            .setArtworkUri(artworkUri)
            .setExtras(bundle)
            .build()
        return itemBuilder
            .setMediaMetadata(metadata)
            .setMediaId(song.id.toString())
            .setUri(song.uri)
            .build()
    }

    private suspend fun getListOfMediaSources(
        songs: List<Song>,
        shuffled: Boolean = false
    ): List<MediaItem> =
        withContext(Dispatchers.Default) {

            val medias = mutableListOf<MediaItem>()

            for (song in songs) {
                medias.add(createMediaItemFromSong(song))
            }

            if (shuffled) {
                medias.shuffle()
            }

            return@withContext medias
        }

    private fun getSongIndex(song: Song): Int? {
        val items = getCurrentMediaItems()
        val index = items.indexOfFirst { it.mediaId == song.id.toString() }
        return if (index >= 0) {
            index
        } else {
            null
        }
    }

    private fun getCurrentMediaItems(): MutableList<MediaItem> {
        val size = exoPlayer.mediaItemCount
        var index = 0
        val items = mutableListOf<MediaItem>()
        while (index < size) {
            items += exoPlayer.getMediaItemAt(index)
            ++index
        }
        return items
    }

    private fun removeSongsFromPlaylist(ids: List<Long>) = lifecycleScope.launch {
        val items = getCurrentMediaItems()
        val toRemove = items.filter { ids.contains(it.mediaId.toLongOrNull()) }
        val indexes = mutableListOf<Int>()

        for (item in toRemove) {
            val index = items.indexOf(item)
            if (index != -1) {
                indexes.add(index)
            }
        }
        indexes.forEach { exoPlayer.removeMediaItem(it) }
    }

    private fun shuffle(shuffle: Boolean) = lifecycleScope.launch {
        exoPlayer.shuffleModeEnabled = shuffle
    }

    private fun seekTo(position: Float) = lifecycleScope.launch {
        exoPlayer.seekTo(position.toLong())
    }

    private fun seekToDefaultPosition(index: Int): Int {
        if (index > exoPlayer.mediaItemCount || index < 0) {
            return 0
        }
        return index
    }

    private fun startNewSession(params: PlaySongAtIndexParams) = lifecycleScope.launch {

        if (params.songs.isEmpty()) return@launch

        exoPlayer.stop()

        exoPlayer.clearMediaItems()

        exoPlayer.addMediaItems(getListOfMediaSources(params.songs, params.shuffled))

        if (!params.shuffled) {
            exoPlayer.seekToDefaultPosition(seekToDefaultPosition(params.index))
        }

        exoPlayer.prepare()

        exoPlayer.play()

        activateSession()
    }

    private fun startNewSessionPaused(
        params: StartNewSessionPausedParams
    ) = lifecycleScope.launch {

        if (params.songs.isEmpty()) return@launch

        exoPlayer.stop()

        exoPlayer.clearMediaItems()

        exoPlayer.addMediaItems(getListOfMediaSources(params.songs))

        exoPlayer.prepare()

        exoPlayer.seekToDefaultPosition(seekToDefaultPosition(params.index))

        exoPlayer.seekTo(params.position)

        activateSession()
    }

    private fun pauseOrPlay() = lifecycleScope.launch {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

    fun next() = lifecycleScope.launch {
        exoPlayer.seekToNextMediaItem()
    }

    fun back() = lifecycleScope.launch {
        exoPlayer.seekToPreviousMediaItem()
    }

    private fun playNextSongs(params: PlayNextSongsParams) = lifecycleScope.launch {
        if (sessionActive) {
            playNext(params)
        } else {
            startNewSession(PlaySongAtIndexParams(params.songs, 0))
        }
        updateHasNextAndPrevious()
    }

    private fun playNext(params: PlayNextSongsParams) = lifecycleScope.launch {
        for (song in params.songs) {
            val songIndex = getSongIndex(song)
            if (songIndex != null) {
                if (songIndex == exoPlayer.currentMediaItemIndex) return@launch
                if (songIndex < exoPlayer.currentMediaItemIndex) {
                    exoPlayer.moveMediaItem(songIndex, exoPlayer.currentMediaItemIndex)
                    return@launch
                }
                exoPlayer.moveMediaItem(songIndex, exoPlayer.nextMediaItemIndex)
                return@launch
            }
            val nextSongIndex = exoPlayer.nextMediaItemIndex
            if (nextSongIndex == C.INDEX_UNSET) {
                exoPlayer.addMediaItem(createMediaItemFromSong(song))
            } else {
                exoPlayer.addMediaItem(nextSongIndex, createMediaItemFromSong(song))
            }
        }
    }

    private fun jumpTo(index: Int) = lifecycleScope.launch {
        exoPlayer.seekTo(index, 0)
    }

    private fun reorder(oldIndex: Int, newIndex: Int) = lifecycleScope.launch {
        exoPlayer.moveMediaItem(oldIndex, newIndex)
        eventsDispatcher.reordered()
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun updateHasNextAndPrevious() {
        eventsDispatcher.hasNextAndPreviousChanged(
            hasNext = exoPlayer.hasNextMediaItem(),
            hasPrevious = exoPlayer.hasPreviousMediaItem()
        ).flowOn(Dispatchers.IO).launchIn(lifecycleScope)
    }

    override fun onRepeatModeChanged(repeatMode: Int) {

        updateHasNextAndPrevious()

        val mode = when (repeatMode) {
            ExoPlayer.REPEAT_MODE_ALL -> {
                PlaybackMode.Loop
            }
            ExoPlayer.REPEAT_MODE_OFF -> {
                PlaybackMode.Repeat
            }
            else -> {
                PlaybackMode.OneTime
            }
        }

        getPlayerSettingsUseCase()
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is GetPlayerSettingsState.Loaded) {
                    updatePlayerSettingsUseCase.updateRepeatMode(
                        PlayerSettings(
                            playbackMode = mode,
                            respectAudioFocus = it.settings.respectAudioFocus
                        )
                    ).flowOn(Dispatchers.IO).launchIn(lifecycleScope)
                }
            }
            .launchIn(lifecycleScope)

        eventsDispatcher.repeatMode(mode)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)

        super.onRepeatModeChanged(repeatMode)
    }

    private fun pauseSessionOnEnded() {
        mediaSession.isActive = false

        timer?.cancel()
        timer = null
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        if (playbackState == ExoPlayer.STATE_ENDED || playbackState == ExoPlayer.STATE_IDLE) {
            pauseSessionOnEnded()
            stopServiceDoNotRemoveNot()
        }
        super.onPlaybackStateChanged(playbackState)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        playerNotificationManager.setMediaSessionToken(mediaSession.sessionToken)
        mediaItem?.let { media ->
            media.mediaId.toLongOrNull()?.let {
                eventsDispatcher.mediaTransition(id = it)
                    .flowOn(Dispatchers.IO)
                    .launchIn(lifecycleScope)
                updateHasNextAndPrevious()
            }
        }
        super.onMediaItemTransition(mediaItem, reason)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        eventsDispatcher.isPlaying(isPlaying)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
        super.onIsPlayingChanged(isPlaying)
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        if (ongoing) {
            startPlayerService(notificationId, notification)
        } else {
            stopServiceDoNotRemoveNot()
        }
        super.onNotificationPosted(notificationId, notification, ongoing)
    }

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        stopForeground(true)
        mediaSession.isActive = false
        super.onNotificationCancelled(notificationId, dismissedByUser)
    }

    override fun onDestroy() {
        playlistJob?.cancel()
        playlistJob = null
        disableSession()
        pauseOnEnded()
        unregisterReceiver(bluetoothReceiver)
        exoPlayer.release()
        mediaSession.release()
        super.onDestroy()
    }
}
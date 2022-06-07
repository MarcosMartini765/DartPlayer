package com.martini.dartplayer.data.local

import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlayNextSongsParams
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerControlsApi @Inject constructor(
    private val playerDispatcher: PlayerDispatcher,
    private val musicDao: MusicDao,
) {
    suspend fun startNewSession(
        params: StartNewSessionParams
    ) = withContext(Dispatchers.IO) {
        val songs = getListOfSongs(params.selected)
        playerDispatcher.startNewSession(
            PlaySongAtIndexParams(
                songs = songs,
                index = params.index,
                shuffled = params.shuffled
            )
        ).launchIn(this)
    }

    suspend fun playNext(
        params: PlayNextParams
    ) = withContext(Dispatchers.IO) {
        val songs = getListOfSongs(params.selected)
        playerDispatcher.playNextSongs(
            PlayNextSongsParams(
                songs = if (params.shuffled) songs.shuffled() else songs,
                shuffled = params.shuffled
            )
        ).launchIn(this)
    }

    private suspend fun getSongsFromArtist(artistWithAlbums: ArtistWithAlbums): MutableList<Song> =
        withContext(Dispatchers.IO) {
            val songs = mutableListOf<Song>()
            artistWithAlbums.albums.map { musicDao.getAlbumWithSongs(it.albumName) }.forEach {
                songs.addAll(it.songs.sortedBy { song -> song.name })
            }
            return@withContext songs
        }

    private suspend fun getListOfSongs(selected: Selected) = withContext(Dispatchers.IO) {
        val songs = mutableListOf<Song>()
        for (entity in selected.music) {
            when (entity) {
                is Song -> songs.add(entity)
                is AlbumWithSongs -> songs.addAll(entity.songs.sortedBy { it.name })
                is ArtistWithAlbums -> getSongsFromArtist(entity)
                is PlaylistWithSongs -> songs.addAll(entity.songs.sortedBy { it.name })
            }
        }
        return@withContext songs
    }
}
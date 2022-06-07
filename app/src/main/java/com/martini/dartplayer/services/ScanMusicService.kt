package com.martini.dartplayer.services

import android.app.*
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.martini.dartplayer.MainActivity
import com.martini.dartplayer.R
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.dispatchers.ScanMusicAction
import com.martini.dartplayer.domain.dispatchers.ScanMusicDispatcher
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.ScanSongsState
import com.martini.dartplayer.domain.usecases.ScanSongsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@FlowPreview
@AndroidEntryPoint
class ScanMusicService : LifecycleService() {

    @Inject
    lateinit var scanSongsUseCase: ScanSongsUseCase

    @Inject
    lateinit var scanMusicDispatcher: ScanMusicDispatcher

    @Inject
    lateinit var loadSongsUseCase: LoadSongsUseCase

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startListener()
    }

    private fun startListener() {
        scanMusicDispatcher.listen
            .onEach {
                when (it) {
                    is ScanMusicAction.Scan -> scan()
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun scan() {
        startForegroundProcess()

        scanSongsUseCase()
            .flowOn(Dispatchers.Default)
            .onEach {
                if (it is ScanSongsState.Loaded) {
                    loadSongs()
                } else if (it is ScanSongsState.Failure) {
                    stopForegroundScanFailed()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun loadSongs() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is LoadSongsState.Loaded || it is LoadSongsState.Failure) {
                    stopForegroundScanSucceed()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun createNotificationChannel() {

        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(
            Constants.SCAN_SONGS_NOTIFICATION_CHANNEL_ID,
            getString(R.string.ScanMusicServiceNotificationName),
            importance
        )
        mChannel.description = getString(R.string.ScanMusicServiceNotificationDescription)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val groupName = getString(R.string.BackgroundServicesNotificationGroup)
        notificationManager.createNotificationChannelGroup(
            NotificationChannelGroup(
                Constants.SCAN_SONGS_GROUP_ID,
                groupName
            )
        )
        mChannel.group = Constants.SCAN_SONGS_GROUP_ID

        notificationManager.createNotificationChannel(mChannel)
    }

    private fun startForegroundProcess() {

        val notification: Notification =
            Notification.Builder(this, Constants.SCAN_SONGS_NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_scan)
                .setContentTitle(getString(R.string.ScanMusicServiceScanningSongs))
                .setContentText(getString(R.string.ScanMusicServiceExplanation))
                .setContentIntent(getPendingIntent())
                .build()

        startForeground(Constants.SCAN_SONGS_NOTIFICATION_ID, notification)
    }

    private fun stopForegroundScanFailed() {
        stopForeground(true)
        showNotificationForResult(getString(R.string.ScanMusicServiceScanFailure))
        stopSelf()
    }

    private fun stopForegroundScanSucceed() {
        stopForeground(true)
        showNotificationForResult(getString(R.string.ScanMusicServiceScanSuccess))
        stopSelf()
    }

    private fun showNotificationForResult(content: String) {

        val notification: Notification =
            Notification.Builder(this, Constants.SCAN_SONGS_NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_scan)
                .setContentTitle(getString(R.string.ScanMusicServiceScanningSongs))
                .setContentText(content)
                .setContentIntent(getPendingIntent())
                .build()


        with(NotificationManagerCompat.from(this)) {
            notify(Constants.SCAN_SONGS_FINISHED_NOT_ID, notification)
        }
    }

    private fun getPendingIntent(): PendingIntent {
        return Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }
    }
}
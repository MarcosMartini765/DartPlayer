package com.martini.dartplayer.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext

@HiltWorker
class RemoveSongFromPlaylistWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    val playerDispatcher: PlayerDispatcher
) : CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val data = inputData.getLongArray(Constants.DELETE_SONG_PLAYLIST_WORKER_KEY)

        data?.let {
            playerDispatcher.removeSongsFromPlaylist(it.toList())
                .flowOn(Dispatchers.IO).launchIn(this)
        }

        Result.success()
    }
}
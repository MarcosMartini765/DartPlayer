package com.martini.dartplayer.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.martini.dartplayer.domain.usecases.ScanSongsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext

@HiltWorker
class ScanMusicWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    val scanSongsUseCase: ScanSongsUseCase
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.Default) {
            scanSongsUseCase().launchIn(this)
            Result.success()
        }
    }
}
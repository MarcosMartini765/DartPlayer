package com.martini.dartplayer.data.local

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.files.LocalFilesDao
import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.presentation.components.settings.local.Frequency
import com.martini.dartplayer.services.ScanMusicWorker
import java.time.Duration
import javax.inject.Inject

class LocalFilesSettingsApi @Inject constructor(
    private val context: Context,
    private val localFilesDao: LocalFilesDao
) {

    private val defaultSettings = LocalFilesSettings(1, Frequency.NEVER)

    suspend fun getSettings(): LocalFilesSettings {
        return localFilesDao.getSettings()
    }

    suspend fun initializeSettings(): LocalFilesSettings {
        localFilesDao.initializeSettings(defaultSettings)
        return localFilesDao.getSettings()
    }

    suspend fun updateSettings(settings: LocalFilesSettings): LocalFilesSettings {
        localFilesDao.updateSettings(settings)

        when (settings.frequency) {
            Frequency.ONCE_DAY -> {
                buildDailyWork()
            }
            Frequency.ONCE_WEEK -> {
                buildWeeklyWork()
            }
            else -> {
                cancelWork()
            }
        }

        return settings
    }

    private fun buildDailyWork() {

        cancelWork()

        val dailyWork = PeriodicWorkRequestBuilder<ScanMusicWorker>(Duration.ofDays(1))
            .addTag(Constants.SCAN_MUSIC_WORKER_TAG)
            .build()

        WorkManager.getInstance(context).enqueue(dailyWork)
    }

    private fun buildWeeklyWork() {

        cancelWork()

        val weeklyWork = PeriodicWorkRequestBuilder<ScanMusicWorker>(Duration.ofHours(84))
            .addTag(Constants.SCAN_MUSIC_WORKER_TAG)
            .build()

        WorkManager.getInstance(context).enqueue(weeklyWork)
    }

    private fun cancelWork() {
        WorkManager.getInstance(context).cancelAllWorkByTag(Constants.SCAN_MUSIC_WORKER_TAG)
    }
}
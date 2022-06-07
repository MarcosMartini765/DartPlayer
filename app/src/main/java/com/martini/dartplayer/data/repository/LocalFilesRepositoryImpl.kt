package com.martini.dartplayer.data.repository

import com.martini.dartplayer.data.local.LocalFilesSettingsApi
import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.domain.repository.LocalFilesRepository
import javax.inject.Inject

class LocalFilesRepositoryImpl @Inject constructor(
    private val localFilesSettingsApi: LocalFilesSettingsApi
) : LocalFilesRepository {
    override suspend fun getSettings(): LocalFilesSettings {
        return localFilesSettingsApi.getSettings()
    }

    override suspend fun initializeSettings(): LocalFilesSettings {
        return localFilesSettingsApi.initializeSettings()
    }

    override suspend fun updateSettings(settings: LocalFilesSettings): LocalFilesSettings {
        return localFilesSettingsApi.updateSettings(settings)
    }

}
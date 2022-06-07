package com.martini.dartplayer.domain.repository

import com.martini.dartplayer.domain.entity.files.LocalFilesSettings

interface LocalFilesRepository {
    suspend fun getSettings(): LocalFilesSettings
    suspend fun initializeSettings(): LocalFilesSettings
    suspend fun updateSettings(settings: LocalFilesSettings): LocalFilesSettings
}
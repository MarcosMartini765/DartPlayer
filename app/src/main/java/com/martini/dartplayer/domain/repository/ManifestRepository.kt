package com.martini.dartplayer.domain.repository

interface ManifestRepository {
    fun getCrashlyticsEnabled(): Boolean
    fun setCrashlyticsCache(value: Boolean)
}
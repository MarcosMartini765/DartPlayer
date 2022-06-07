package com.martini.dartplayer.data.repository

import com.martini.dartplayer.data.local.ManifestApi
import com.martini.dartplayer.domain.repository.ManifestRepository
import javax.inject.Inject

class ManifestRepositoryImpl @Inject constructor(
    private val manifestApi: ManifestApi
) : ManifestRepository {
    override fun getCrashlyticsEnabled(): Boolean {
        return manifestApi.getCrashlyticsEnabled()
    }

    override fun setCrashlyticsCache(value: Boolean) {
        return manifestApi.setCrashlyticsCached(value)
    }
}
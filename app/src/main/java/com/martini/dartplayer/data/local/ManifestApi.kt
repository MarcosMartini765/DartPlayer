package com.martini.dartplayer.data.local

import android.content.Context
import android.content.pm.PackageManager
import com.martini.dartplayer.common.Constants
import javax.inject.Inject

class ManifestApi @Inject constructor(
    private val context: Context
) {
    fun getCrashlyticsEnabled(): Boolean {
        val store = context.getSharedPreferences(Constants.CRASHLYTICS_DB, Context.MODE_PRIVATE)

        val info = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )

        val enabled = info.metaData["firebase_crashlytics_collection_enabled"] as Boolean

        return store.getBoolean(Constants.CRASHLYTICS_BOOLEAN, enabled)
    }

    fun setCrashlyticsCached(value: Boolean) {
        val store = context.getSharedPreferences(Constants.CRASHLYTICS_DB, Context.MODE_PRIVATE)
        with(store.edit()) {
            putBoolean(Constants.CRASHLYTICS_BOOLEAN, value)
            commit()
        }
    }
}
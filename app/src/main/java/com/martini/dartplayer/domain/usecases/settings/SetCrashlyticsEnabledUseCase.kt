package com.martini.dartplayer.domain.usecases.settings

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.martini.dartplayer.domain.repository.ManifestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetCrashlyticsEnabledUseCase @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
    private val manifestRepository: ManifestRepository
) {
    val listen = MutableStateFlow<SetCrashlyticsEnabledState>(SetCrashlyticsEnabledState.Loading)

    operator fun invoke(value: Boolean) = flow {
        try {
            emit(SetCrashlyticsEnabledState.Loading)
            listen.emit(SetCrashlyticsEnabledState.Loading)
            crashlytics.setCrashlyticsCollectionEnabled(value)
            manifestRepository.setCrashlyticsCache(value)
            emit(SetCrashlyticsEnabledState.Loaded)
            listen.emit(SetCrashlyticsEnabledState.Loaded)
        } catch (e: Exception) {
            emit(SetCrashlyticsEnabledState.Failure)
            listen.emit(SetCrashlyticsEnabledState.Failure)
        }
    }
}

sealed class SetCrashlyticsEnabledState {
    object Loading : SetCrashlyticsEnabledState()
    object Loaded : SetCrashlyticsEnabledState()
    object Failure : SetCrashlyticsEnabledState()
}
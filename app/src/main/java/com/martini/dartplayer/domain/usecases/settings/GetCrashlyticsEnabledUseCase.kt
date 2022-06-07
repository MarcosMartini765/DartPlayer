package com.martini.dartplayer.domain.usecases.settings

import com.martini.dartplayer.domain.repository.ManifestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCrashlyticsEnabledUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository
) {

    val listen = MutableStateFlow<GetCrashlyticsEnabledState>(GetCrashlyticsEnabledState.Loading)

    operator fun invoke() = flow {
        try {
            val enabled = manifestRepository.getCrashlyticsEnabled()
            emit(GetCrashlyticsEnabledState.Loaded(enabled))
            listen.emit(GetCrashlyticsEnabledState.Loaded(enabled))
        } catch (e: Exception) {
            listen.emit(GetCrashlyticsEnabledState.Failure)
            emit(GetCrashlyticsEnabledState.Failure)
        }
    }
}

sealed class GetCrashlyticsEnabledState {
    object Loading : GetCrashlyticsEnabledState()
    class Loaded(
        val enabled: Boolean
    ) : GetCrashlyticsEnabledState()

    object Failure : GetCrashlyticsEnabledState()
}
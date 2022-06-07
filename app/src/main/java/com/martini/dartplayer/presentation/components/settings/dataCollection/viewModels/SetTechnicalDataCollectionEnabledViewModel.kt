package com.martini.dartplayer.presentation.components.settings.dataCollection.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.settings.SetCrashlyticsEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SetTechnicalDataCollectionEnabledViewModel @Inject constructor(
    private val setCrashlyticsEnabledUseCase: SetCrashlyticsEnabledUseCase
) : ViewModel() {
    operator fun invoke(enabled: Boolean) {
        setCrashlyticsEnabledUseCase(enabled)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
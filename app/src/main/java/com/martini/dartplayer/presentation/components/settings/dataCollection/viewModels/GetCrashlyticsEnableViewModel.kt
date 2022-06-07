package com.martini.dartplayer.presentation.components.settings.dataCollection.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.settings.GetCrashlyticsEnabledState
import com.martini.dartplayer.domain.usecases.settings.GetCrashlyticsEnabledUseCase
import com.martini.dartplayer.domain.usecases.settings.SetCrashlyticsEnabledState
import com.martini.dartplayer.domain.usecases.settings.SetCrashlyticsEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetCrashlyticsEnableViewModel @Inject constructor(
    private val getCrashlyticsEnabledUseCase: GetCrashlyticsEnabledUseCase,
    private val setCrashlyticsEnabledUseCase: SetCrashlyticsEnabledUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<GetCrashlyticsEnabledState>(GetCrashlyticsEnabledState.Loading)

    val state: State<GetCrashlyticsEnabledState> = _state

    private fun listen() {
        getCrashlyticsEnabledUseCase.listen
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
        setCrashlyticsEnabledUseCase.listen
            .onEach {
                if (it is SetCrashlyticsEnabledState.Loaded) {
                    invoke()
                }
            }
            .launchIn(viewModelScope)
    }

    init {
        listen()
        invoke()
    }

    operator fun invoke() {
        getCrashlyticsEnabledUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
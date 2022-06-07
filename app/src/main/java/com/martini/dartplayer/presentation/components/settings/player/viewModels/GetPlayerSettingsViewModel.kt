package com.martini.dartplayer.presentation.components.settings.player.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsState
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetPlayerSettingsViewModel @Inject constructor(
    private val getPlayerSettingsUseCase: GetPlayerSettingsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetPlayerSettingsState>(GetPlayerSettingsState.Initial)

    val state: State<GetPlayerSettingsState> = _state

    init {
        listen()
        invoke()
    }

    operator fun invoke() {
        getPlayerSettingsUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        getPlayerSettingsUseCase.listen
            .onEach {
                _state.value = it
            }
            .launchIn(viewModelScope)
    }
}
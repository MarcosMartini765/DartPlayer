package com.martini.dartplayer.presentation.components.settings.player.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsUseCase
import com.martini.dartplayer.domain.usecases.playerSettings.UpdatePlayerSettingsState
import com.martini.dartplayer.domain.usecases.playerSettings.UpdatePlayerSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UpdatePlayerSettingsViewModel @Inject constructor(
    private val updatePlayerSettingsUseCase: UpdatePlayerSettingsUseCase,
    private val getPlayerSettingsUseCase: GetPlayerSettingsUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<UpdatePlayerSettingsState>(UpdatePlayerSettingsState.Initial)

    val state: State<UpdatePlayerSettingsState> = _state

    operator fun invoke(settings: PlayerSettings) {
        updatePlayerSettingsUseCase(settings)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is UpdatePlayerSettingsState.Loaded -> {
                        getPlayerSettingsUseCase()
                            .flowOn(Dispatchers.IO)
                            .launchIn(viewModelScope)
                    }
                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }
}
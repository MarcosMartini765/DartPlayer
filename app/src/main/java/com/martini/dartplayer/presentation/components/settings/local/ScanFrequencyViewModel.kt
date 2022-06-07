package com.martini.dartplayer.presentation.components.settings.local

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.domain.usecases.files.GetLocalFilesSettingsUseCase
import com.martini.dartplayer.domain.usecases.files.GetLocalFilesState
import com.martini.dartplayer.domain.usecases.files.UpdateLocalFilesSettingsUseCase
import com.martini.dartplayer.domain.usecases.files.UpdateLocalFilesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScanFrequencyViewModel @Inject constructor(
    private val getLocalFilesSettingsUseCase: GetLocalFilesSettingsUseCase,
    private val updateLocalFilesSettingsUseCase: UpdateLocalFilesSettingsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetLocalFilesState>(GetLocalFilesState.Initial)

    val state: State<GetLocalFilesState> = _state

    init {
        getSettings()
        listen()
    }

    private fun getSettings() {
        getLocalFilesSettingsUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        getLocalFilesSettingsUseCase.listen
            .onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }

    fun updateSettings(settings: LocalFilesSettings) {
        updateLocalFilesSettingsUseCase(settings)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is UpdateLocalFilesState.Loaded) {
                    getSettings()
                }
            }
            .launchIn(viewModelScope)
    }
}
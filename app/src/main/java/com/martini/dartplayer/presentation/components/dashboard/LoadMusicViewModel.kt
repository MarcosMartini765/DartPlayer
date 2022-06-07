package com.martini.dartplayer.presentation.components.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoadMusicViewModel @Inject constructor(
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<LoadSongsState>(LoadSongsState.Initial)
    val state: State<LoadSongsState> = _state

    init {
        loadMusic()
        setupListener()
    }

    private fun loadMusic() {
        loadSongsUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun setupListener() {
        loadSongsUseCase.listen
            .onEach { result ->
                _state.value = result
            }.launchIn(viewModelScope)
    }

    fun search(text: String) {
        loadSongsUseCase.search(text)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    @FlowPreview
    fun silentLoad() {
        loadSongsUseCase.loadSilently()
            .debounce(Constants.DEBOUNCE_TIME)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}
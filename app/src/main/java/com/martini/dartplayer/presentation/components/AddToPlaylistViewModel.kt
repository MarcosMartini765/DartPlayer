package com.martini.dartplayer.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.params.AddToPlaylistParams
import com.martini.dartplayer.domain.usecases.AddToPlaylistState
import com.martini.dartplayer.domain.usecases.AddToPlaylistUseCase
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddToPlaylistViewModel @Inject constructor(
    private val addToPlaylistUseCase: AddToPlaylistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<AddToPlaylistState>(AddToPlaylistState.Initial)

    val state: State<AddToPlaylistState> = _state

    operator fun invoke(
        params: AddToPlaylistParams
    ) {
        addToPlaylistUseCase(params)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is AddToPlaylistState.Loaded) {
                    loadMusic()
                }
                _state.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun loadMusic() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
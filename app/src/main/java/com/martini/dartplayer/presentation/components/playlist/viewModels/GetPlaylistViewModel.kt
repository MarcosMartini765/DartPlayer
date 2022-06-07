package com.martini.dartplayer.presentation.components.playlist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetPlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<GetPlaylistState>(GetPlaylistState.Initial)

    val state: State<GetPlaylistState> = _state

    private fun listen() {
        getPlaylistUseCase.listen
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    operator fun invoke(name: String) {
        getPlaylistUseCase(name)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    init {
        listen()

        val playlist: String? = savedStateHandle[Constants.PLAYLIST_SCREEN_ARG_NAME]
        playlist?.let {
            invoke(it)
        }
    }
}
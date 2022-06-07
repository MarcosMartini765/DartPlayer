package com.martini.dartplayer.presentation.components.artist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState
import com.martini.dartplayer.domain.usecases.artist.LoadArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoadArtistViewModel @Inject constructor(
    private val loadArtistUseCase: LoadArtistUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<LoadArtistState>(LoadArtistState.Initial)

    val state: State<LoadArtistState> = _state

    init {
        val artist: String? = savedStateHandle[Constants.ARTIST_SCREEN_ARGUMENT_NAME]
        artist?.let {
            getArtist(it)
            listen()
        }
    }

    private fun listen() {
        loadArtistUseCase.listen
            .onEach {
                _state.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun getArtist(artist: String) {
        loadArtistUseCase(artist)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
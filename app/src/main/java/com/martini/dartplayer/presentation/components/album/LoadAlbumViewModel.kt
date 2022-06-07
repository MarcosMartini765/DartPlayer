package com.martini.dartplayer.presentation.components.album

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.domain.usecases.album.LoadAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoadAlbumViewModel @Inject constructor(
    private val loadAlbumUseCase: LoadAlbumUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<LoadAlbumState>(LoadAlbumState.Initial)

    val state: State<LoadAlbumState> = _state

    init {
        val albumName: String? = savedStateHandle[Constants.ALBUM_SCREEN_ARGUMENT_NAME]
        albumName?.let {
            loadAlbum(it)
            listen()
        }
    }

    private fun listen() {
        loadAlbumUseCase.listen
            .onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }

    private fun loadAlbum(album: String) {
        loadAlbumUseCase(album)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
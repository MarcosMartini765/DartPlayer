package com.martini.dartplayer.presentation.components.albumForArtist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.params.albumForArtist.LoadAlbumForArtistParams
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistState
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoadAlbumForArtistViewModel @Inject constructor(
    private val loadAlbumForArtistUseCase: LoadAlbumForArtistUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf<LoadAlbumForArtistState>(LoadAlbumForArtistState.Initial)

    val state: State<LoadAlbumForArtistState> = _state

    init {

        val albumName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ALBUM_ARG]
        val artistName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ARTIST_ARG]

        albumName?.let { album ->
            artistName?.let { artist ->
                start(
                    LoadAlbumForArtistParams(
                        albumName = album,
                        artistName = artist
                    )
                )
                listen()
            }
        }
    }

    private fun start(loadAlbumForArtistParams: LoadAlbumForArtistParams) {
        loadAlbumForArtistUseCase(loadAlbumForArtistParams)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    operator fun invoke(loadAlbumForArtistParams: LoadAlbumForArtistParams) {
        loadAlbumForArtistUseCase(loadAlbumForArtistParams)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        loadAlbumForArtistUseCase.listen
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }
}
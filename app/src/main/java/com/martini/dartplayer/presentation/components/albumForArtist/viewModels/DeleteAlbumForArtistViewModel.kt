package com.martini.dartplayer.presentation.components.albumForArtist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.params.albumForArtist.DeleteAlbumForArtistParams
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteAlbumForArtistState
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteAlbumForArtistUseCase
import com.martini.dartplayer.domain.usecases.artist.LoadArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteAlbumForArtistViewModel @Inject constructor(
    private val deleteAlbumForArtistUseCase: DeleteAlbumForArtistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val loadArtistUseCase: LoadArtistUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state =
        mutableStateOf<DeleteAlbumForArtistState>(DeleteAlbumForArtistState.Initial)

    val state: State<DeleteAlbumForArtistState> = _state

    init {
        listen()
    }

    operator fun invoke(
        albumWithSongs: AlbumWithSongs
    ) {
        var artistName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ARTIST_ARG]

        if (artistName == null) {
            artistName = savedStateHandle[Constants.ARTIST_SCREEN_ARGUMENT_NAME]
        }

        artistName?.let { artist ->
            deleteAlbumForArtistUseCase(
                DeleteAlbumForArtistParams(
                    albumWithSongs = albumWithSongs,
                    artistName = artist
                )
            )
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    private fun listen() {
        deleteAlbumForArtistUseCase.listen
            .onEach {
                _state.value = it

                if (it is DeleteAlbumForArtistState.Loaded) {
                    loadArtist()
                    loadSongs()
                }

            }
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase().flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun loadArtist() {
        var artistName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ARTIST_ARG]

        if (artistName == null) {
            artistName = savedStateHandle[Constants.ARTIST_SCREEN_ARGUMENT_NAME]
        }

        artistName?.let {
            loadArtistUseCase(it).flowOn(Dispatchers.IO).launchIn(viewModelScope)
        }
    }
}
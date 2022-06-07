package com.martini.dartplayer.presentation.components.albumForArtist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.albumForArtist.LoadAlbumForArtistParams
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteSongsForAlbumState
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteSongsForAlbumUseCase
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistUseCase
import com.martini.dartplayer.domain.usecases.artist.LoadArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteSongsForAlbumViewModel @Inject constructor(
    private val deleteSongsForAlbumUseCase: DeleteSongsForAlbumUseCase,
    private val loadArtistUseCase: LoadArtistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val selectedMusicUseCase: SelectedMusicUseCase,
    private val loadAlbumForArtistUseCase: LoadAlbumForArtistUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf<DeleteSongsForAlbumState>(DeleteSongsForAlbumState.Initial)

    val state: State<DeleteSongsForAlbumState> = _state

    init {
        listen()
    }

    operator fun invoke(songs: List<Song>) {
        deleteSongsForAlbumUseCase(songs)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteSongsForAlbumUseCase.listen
            .onEach {
                _state.value = it

                if (it is DeleteSongsForAlbumState.Loaded) {
                    loadAlbum()
                    loadSongs()
                    loadArtist()
                    clearSelected()
                }

            }.launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun loadArtist() {
        val artistName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ARTIST_ARG]
        artistName?.let {
            loadArtistUseCase(it).flowOn(Dispatchers.IO).launchIn(viewModelScope)
        }
    }

    private fun clearSelected() {
        selectedMusicUseCase.clear().flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }

    private fun loadAlbum() {
        val albumName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ALBUM_ARG]
        val artistName: String? = savedStateHandle[Constants.ALBUM_FOR_ARTIST_ARTIST_ARG]

        albumName?.let { album ->
            artistName?.let { artist ->
                loadAlbumForArtistUseCase(
                    LoadAlbumForArtistParams(
                        albumName = album,
                        artistName = artist
                    )
                ).flowOn(Dispatchers.IO).launchIn(viewModelScope)
            }
        }
    }
}
package com.martini.dartplayer.presentation.components.artist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.artist.DeleteAlbumsArtistState
import com.martini.dartplayer.domain.usecases.artist.DeleteAlbumsArtistUseCase
import com.martini.dartplayer.domain.usecases.artist.LoadArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteAlbumArtistViewModel @Inject constructor(
    private val deleteAlbumsArtistUseCase: DeleteAlbumsArtistUseCase,
    private val loadArtistUseCase: LoadArtistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val selectedMusicUseCase: SelectedMusicUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf<DeleteAlbumsArtistState>(DeleteAlbumsArtistState.Initial)

    val state: State<DeleteAlbumsArtistState> = _state

    init {
        listen()
    }

    operator fun invoke(
        artist: ArtistWithAlbumsAndSongs,
        albums: List<AlbumWithSongs>
    ) {
        deleteAlbumsArtistUseCase(artist, albums)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteAlbumsArtistUseCase.listen
            .onEach {
                _state.value = it

                if (it is DeleteAlbumsArtistState.Loaded) {
                    clearSelection()
                    loadArtist()
                    refreshList()
                }

            }
            .launchIn(viewModelScope)
    }

    private fun loadArtist() {
        val artist: String? = savedStateHandle[Constants.ARTIST_SCREEN_ARGUMENT_NAME]
        artist?.let {
            loadArtistUseCase(it)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    private fun refreshList() {
        loadSongsUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun clearSelection() {
        selectedMusicUseCase.clear()
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }
}
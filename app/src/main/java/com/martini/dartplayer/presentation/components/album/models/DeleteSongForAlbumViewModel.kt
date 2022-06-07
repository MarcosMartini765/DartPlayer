package com.martini.dartplayer.presentation.components.album.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.album.DeleteSongsForDashboardAlbumState
import com.martini.dartplayer.domain.usecases.album.DeleteSongsForDashboardAlbumUseCase
import com.martini.dartplayer.domain.usecases.album.LoadAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteSongForAlbumViewModel @Inject constructor(
    private val deleteSongsForDashboardAlbumUseCase: DeleteSongsForDashboardAlbumUseCase,
    private val loadAlbumUseCase: LoadAlbumUseCase,
    private val selectedMusicUseCase: SelectedMusicUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state =
        mutableStateOf<DeleteSongsForDashboardAlbumState>(DeleteSongsForDashboardAlbumState.Initial)

    val state: State<DeleteSongsForDashboardAlbumState> = _state

    init {
        listen()
    }

    operator fun invoke(songs: List<Song>) {
        deleteSongsForDashboardAlbumUseCase(songs)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteSongsForDashboardAlbumUseCase.listen
            .onEach {
                _state.value = it

                if (it is DeleteSongsForDashboardAlbumState.Loaded) {
                    clearSelected()
                    loadAlbum()
                    loadSongs()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadAlbum() {
        val albumName: String? = savedStateHandle[Constants.ALBUM_SCREEN_ARGUMENT_NAME]
        albumName?.let {
            loadAlbumUseCase(it)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    private fun clearSelected() {
        selectedMusicUseCase.clear()
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
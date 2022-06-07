package com.martini.dartplayer.presentation.components.playlist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistUseCase
import com.martini.dartplayer.domain.usecases.playlist.RemoveSongFromPlaylistState
import com.martini.dartplayer.domain.usecases.playlist.RemoveSongFromPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RemoveSongFromPlaylistVIewModel @Inject constructor(
    private val removeSongFromPlaylistUseCase: RemoveSongFromPlaylistUseCase,
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val selectedMusicUseCase: SelectedMusicUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<RemoveSongFromPlaylistState>(RemoveSongFromPlaylistState.Loading)

    val state: State<RemoveSongFromPlaylistState> = _state

    operator fun invoke(selected: Selected, playlist: PlaylistWithSongs) {
        removeSongFromPlaylistUseCase(selected, playlist)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is RemoveSongFromPlaylistState.Loaded) {
                    loadPlaylist()
                    loadSongs()
                    clearSelected()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun clearSelected() {
        selectedMusicUseCase.clear()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun loadPlaylist() {
        val playlist: String? = savedStateHandle[Constants.PLAYLIST_SCREEN_ARG_NAME]
        playlist?.let {
            getPlaylistUseCase(it)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }
}
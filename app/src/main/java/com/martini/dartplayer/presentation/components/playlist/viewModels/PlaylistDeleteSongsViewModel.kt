package com.martini.dartplayer.presentation.components.playlist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.usecases.DeleteMusicState
import com.martini.dartplayer.domain.usecases.DeleteMusicUseCase
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlaylistDeleteSongsViewModel @Inject constructor(
    private val deleteMusicUseCase: DeleteMusicUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf<DeleteMusicState>(DeleteMusicState.Initial)

    val state: State<DeleteMusicState> = _state

    init {
        listen()
    }

    operator fun invoke(selected: Selected) {
        deleteMusicUseCase(selected)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteMusicUseCase.listen
            .onEach {
                _state.value = it
                if (it is DeleteMusicState.Loaded) {
                    loadSongs()
                    getPlaylist()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase.loadSilently().flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun getPlaylist() {
        val playlist: String? = savedStateHandle[Constants.PLAYLIST_SCREEN_ARG_NAME]
        playlist?.let {
            getPlaylistUseCase(it)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }
}
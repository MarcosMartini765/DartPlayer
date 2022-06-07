package com.martini.dartplayer.presentation.components.playlist.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.playlist.DeletePlaylistState
import com.martini.dartplayer.domain.usecases.playlist.DeletePlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeletePlaylistViewModel @Inject constructor(
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<DeletePlaylistState>(DeletePlaylistState.Loading)

    val state: State<DeletePlaylistState> = _state

    private fun listen() {
        deletePlaylistUseCase.listen
            .onEach {
                if (it is DeletePlaylistState.Loaded) {
                    loadSongs()
                }
                _state.value = it
            }
            .launchIn(viewModelScope)
    }

    init {
        listen()
    }

    operator fun invoke(playlistWithSongs: PlaylistWithSongs) {
        deletePlaylistUseCase(playlistWithSongs)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
package com.martini.dartplayer.presentation.components.album.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.album.DeleteAlbumForAlbumState
import com.martini.dartplayer.domain.usecases.album.DeleteAlbumForAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteAlbumForAlbumViewModel @Inject constructor(
    private val deleteAlbumForAlbumUseCase: DeleteAlbumForAlbumUseCase,
    private val selectedMusicUseCase: SelectedMusicUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
) : ViewModel() {
    private val _state = mutableStateOf<DeleteAlbumForAlbumState>(DeleteAlbumForAlbumState.Initial)

    val state: State<DeleteAlbumForAlbumState> = _state

    init {
        listen()
    }

    operator fun invoke(album: AlbumWithSongs) {

        deleteAlbumForAlbumUseCase(album)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteAlbumForAlbumUseCase.listen
            .onEach {
                _state.value = it

                if (it is DeleteAlbumForAlbumState.Loaded) {
                    clearSelected()
                    loadSongs()
                }
            }
            .launchIn(viewModelScope)
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
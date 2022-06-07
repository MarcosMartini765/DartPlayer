package com.martini.dartplayer.presentation.components.toDelete.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.toDelete.DeletePlaylistsState
import com.martini.dartplayer.domain.usecases.toDelete.DeletePlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeletePlaylistsViewModel @Inject constructor(
    private val selectedMusicUseCase: SelectedMusicUseCase,
    private val deletePlaylistsUseCase: DeletePlaylistsUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    operator fun invoke(playlists: List<PlaylistWithSongs>) {
        deletePlaylistsUseCase(playlists)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is DeletePlaylistsState.Loaded) {
                    selectedMusicUseCase.removePlaylist(playlists)
                        .flowOn(Dispatchers.IO)
                        .launchIn(viewModelScope)
                    loadSongsUseCase.loadSilently()
                        .flowOn(Dispatchers.IO)
                        .launchIn(viewModelScope)
                }
            }
            .launchIn(viewModelScope)
    }
}
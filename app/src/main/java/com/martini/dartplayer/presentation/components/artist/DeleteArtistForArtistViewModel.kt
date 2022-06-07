package com.martini.dartplayer.presentation.components.artist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.artist.DeleteArtistForArtistState
import com.martini.dartplayer.domain.usecases.artist.DeleteArtistForArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeleteArtistForArtistViewModel @Inject constructor(
    private val deleteArtistForArtistUseCase: DeleteArtistForArtistUseCase,
    private val loadSongsUseCase: LoadSongsUseCase,
    private val selectedMusicUseCase: SelectedMusicUseCase,
) : ViewModel() {
    private val _state =
        mutableStateOf<DeleteArtistForArtistState>(DeleteArtistForArtistState.Initial)

    val state: State<DeleteArtistForArtistState> = _state

    init {
        listen()
    }

    operator fun invoke(artist: ArtistWithAlbumsAndSongs) {
        deleteArtistForArtistUseCase(artist)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        deleteArtistForArtistUseCase.listen
            .onEach {
                _state.value = it

                if (it is DeleteArtistForArtistState.Loaded) {
                    clearSelection()
                    refreshList()
                }

            }
            .launchIn(viewModelScope)
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
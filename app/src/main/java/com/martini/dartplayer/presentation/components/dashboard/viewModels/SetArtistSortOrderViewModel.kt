package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.artist.SetArtistSortOrderState
import com.martini.dartplayer.domain.usecases.artist.SetArtistSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SetArtistSortOrderViewModel @Inject constructor(
    private val setArtistSortOrderUseCase: SetArtistSortOrderUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<SetArtistSortOrderState>(SetArtistSortOrderState.Loading)

    val state: State<SetArtistSortOrderState> = _state

    operator fun invoke(order: SortOrder) {
        setArtistSortOrderUseCase(order)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is SetArtistSortOrderState.Loaded) {
                    loadSongs()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadSongs() {
        loadSongsUseCase.loadSilently()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
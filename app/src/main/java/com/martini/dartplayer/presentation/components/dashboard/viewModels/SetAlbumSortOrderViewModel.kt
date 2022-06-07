package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.album.SetAlbumSortOrderState
import com.martini.dartplayer.domain.usecases.album.SetAlbumSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SetAlbumSortOrderViewModel @Inject constructor(
    private val setAlbumSortOrderUseCase: SetAlbumSortOrderUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<SetAlbumSortOrderState>(SetAlbumSortOrderState.Loading)

    val state: State<SetAlbumSortOrderState> = _state

    operator fun invoke(order: SortOrder) {
        setAlbumSortOrderUseCase(order)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is SetAlbumSortOrderState.Loaded) {
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
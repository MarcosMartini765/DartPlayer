package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.usecases.LoadSongsUseCase
import com.martini.dartplayer.domain.usecases.song.SetSongSortOrderState
import com.martini.dartplayer.domain.usecases.song.SetSongSortOrderUseCase
import com.martini.dartplayer.domain.usecases.song.GetSongSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SetSongSortOrderViewModel @Inject constructor(
    private val setSongSortOrderUseCase: SetSongSortOrderUseCase,
    private val getSongSortOrderUseCase: GetSongSortOrderUseCase,
    private val loadSongsUseCase: LoadSongsUseCase
) : ViewModel() {
    operator fun invoke(order: SortOrder) {
        setSongSortOrderUseCase(order)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is SetSongSortOrderState.Loaded) {
                    getOrder()
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

    private fun getOrder() {
        getSongSortOrderUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
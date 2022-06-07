package com.martini.dartplayer.presentation.components.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.song.GetSongSortOrderState
import com.martini.dartplayer.domain.usecases.song.GetSongSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetSongSortOrderViewModel @Inject constructor(
    private val getSongSortOrderUseCase: GetSongSortOrderUseCase
) : ViewModel() {

    private val _state = mutableStateOf<GetSongSortOrderState>(GetSongSortOrderState.Loading)

    val state: State<GetSongSortOrderState> = _state

    private fun listen() {
        getSongSortOrderUseCase.listen
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    init {
        listen()
        invoke()
    }

    operator fun invoke() {
        getSongSortOrderUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
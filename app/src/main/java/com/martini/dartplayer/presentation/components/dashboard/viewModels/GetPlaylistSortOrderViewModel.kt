package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistSortOrderState
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetPlaylistSortOrderViewModel @Inject constructor(
    private val getPlaylistSortOrderUseCase: GetPlaylistSortOrderUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetPlaylistSortOrderState>(GetPlaylistSortOrderState.Loading)

    val state: State<GetPlaylistSortOrderState> = _state

    operator fun invoke() {
        getPlaylistSortOrderUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    init {
        invoke()
    }
}
package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.album.GetAlbumSortOrderState
import com.martini.dartplayer.domain.usecases.album.GetAlbumSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetAlbumSortOrderViewModel @Inject constructor(
    private val getAlbumSortOrderUseCase: GetAlbumSortOrderUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetAlbumSortOrderState>(GetAlbumSortOrderState.Loading)

    val state: State<GetAlbumSortOrderState> = _state

    operator fun invoke() {
        getAlbumSortOrderUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    init {
        invoke()
    }
}
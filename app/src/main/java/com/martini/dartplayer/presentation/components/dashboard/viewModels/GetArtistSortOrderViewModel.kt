package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.artist.GetArtistSortOrderState
import com.martini.dartplayer.domain.usecases.artist.GetArtistSortOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetArtistSortOrderViewModel @Inject constructor(
    private val getArtistSortOrderUseCase: GetArtistSortOrderUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetArtistSortOrderState>(GetArtistSortOrderState.Loading)

    val state: State<GetArtistSortOrderState> = _state

    operator fun invoke() {
        getArtistSortOrderUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    init {
        invoke()
    }
}
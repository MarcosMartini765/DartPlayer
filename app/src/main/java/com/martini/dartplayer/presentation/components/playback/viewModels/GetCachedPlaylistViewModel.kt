package com.martini.dartplayer.presentation.components.playback.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import com.martini.dartplayer.domain.usecases.playerService.GetCachedPlaylistState
import com.martini.dartplayer.domain.usecases.playerService.GetCachedPlaylistUseCase
import com.martini.dartplayer.domain.usecases.playerService.SetCachedPlaylistState
import com.martini.dartplayer.domain.usecases.playerService.SetCachedPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetCachedPlaylistViewModel @Inject constructor(
    private val getCachedPlaylistUseCase: GetCachedPlaylistUseCase,
    private val setCachedPlaylistUseCase: SetCachedPlaylistUseCase,
    private val eventsDispatcher: PlayerEventsDispatcher,
    private val playerDispatcher: PlayerDispatcher,
) : ViewModel() {
    private val _state = mutableStateOf<GetCachedPlaylistState>(GetCachedPlaylistState.Initial)

    val state: State<GetCachedPlaylistState> = _state

    init {
        listen()
        invoke()
    }

    private fun listenReorder() {
        eventsDispatcher.reorderListener
            .onEach { invoke() }
            .launchIn(viewModelScope)
        setCachedPlaylistUseCase.listen
            .onEach {
                if (it is SetCachedPlaylistState.Loaded) {
                    invoke()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun listen() {
        listenReorder()
        setCachedPlaylistUseCase.listen
            .onEach {
                when (it) {
                    is SetCachedPlaylistState.Loaded -> {
                        invoke()
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    operator fun invoke() {
        getCachedPlaylistUseCase()
            .flowOn(Dispatchers.IO)
            .onEach {
                _state.value = it
            }
            .launchIn(viewModelScope)
    }

    fun jumpTo(index: Int) {
        playerDispatcher.jumpTo(index)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun reorder(oldIndex: Int, newIndex: Int) {
        playerDispatcher.reorder(oldIndex, newIndex)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
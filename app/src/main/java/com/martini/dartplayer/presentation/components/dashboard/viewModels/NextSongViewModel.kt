package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.dispatchers.player.PlayerEvent
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NextSongViewModel @Inject constructor(
    private val playerDispatcher: PlayerDispatcher,
    private val playerEventsDispatcher: PlayerEventsDispatcher
) : ViewModel() {

    private val _state = mutableStateOf(false)

    val state: State<Boolean> = _state

    init {
        listen()
    }

    private fun listen() {
        playerEventsDispatcher.hasNextAndPreviousListener
            .onEach {
                if (it is PlayerEvent.HasNextAndPrevious) {
                    _state.value = it.hasNext
                }
            }
            .launchIn(viewModelScope)
    }

    operator fun invoke() {
        playerDispatcher.next()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
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
class PlayOrPauseViewModel @Inject constructor(
    private val playerEventsDispatcher: PlayerEventsDispatcher,
    private val playerDispatcher: PlayerDispatcher
) : ViewModel() {

    private val _state = mutableStateOf(false)

    val state: State<Boolean> = _state

    init {
        listen()
    }

    operator fun invoke() {
        playerDispatcher.pauseOrPlay()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        playerEventsDispatcher.isPlayingListener
            .onEach {
                if (it is PlayerEvent.PlayerIsPlaying) {
                    _state.value = it.isPlaying
                }
            }
            .launchIn(viewModelScope)
    }
}
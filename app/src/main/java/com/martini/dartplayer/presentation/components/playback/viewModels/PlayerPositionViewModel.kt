package com.martini.dartplayer.presentation.components.playback.viewModels

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
class PlayerPositionViewModel @Inject constructor(
    private val playerEventsDispatcher: PlayerEventsDispatcher,
    private val playerDispatcher: PlayerDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(0F)

    val state: State<Float> = _state

    init {
        listen()
    }

    private fun listen() {
        playerEventsDispatcher.positionListener
            .onEach {
                when (it) {
                    is PlayerEvent.Position -> {
                        _state.value = it.position.toFloat()
                    }
                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    operator fun invoke(newPosition: Float) {
        playerDispatcher.seekTo(newPosition)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
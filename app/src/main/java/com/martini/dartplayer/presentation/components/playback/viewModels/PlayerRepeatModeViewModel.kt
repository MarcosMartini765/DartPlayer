package com.martini.dartplayer.presentation.components.playback.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.dispatchers.player.PlayerEvent
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import com.martini.dartplayer.domain.entity.player.PlaybackMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlayerRepeatModeViewModel @Inject constructor(
    private val eventsDispatcher: PlayerEventsDispatcher,
    private val playerDispatcher: PlayerDispatcher
) : ViewModel() {

    private val _state = mutableStateOf(PlaybackMode.Repeat)

    val state: State<PlaybackMode> = _state

    private fun listen() {
        eventsDispatcher.repeatListener
            .onEach {
                if (it is PlayerEvent.RepeatMode) {
                    _state.value = it.mode
                }
            }
            .launchIn(viewModelScope)
    }

    init {
        listen()
    }

    operator fun invoke(mode: PlaybackMode) {
        val newMode = when(mode) {
            PlaybackMode.OneTime -> PlaybackMode.Loop
            PlaybackMode.Loop -> PlaybackMode.Repeat
            PlaybackMode.Repeat -> PlaybackMode.OneTime
        }
        playerDispatcher.repeatMode(newMode)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
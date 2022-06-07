package com.martini.dartplayer.presentation.components.viewModels

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
class PlayerShuffleViewModel @Inject constructor(
    private val playerDispatcher: PlayerDispatcher,
    private val eventsDispatcher: PlayerEventsDispatcher
) : ViewModel() {
    private val _enabled = mutableStateOf(false)

    val enabled: State<Boolean> = _enabled

    private fun listen() {
        eventsDispatcher.shuffleListener
            .onEach {
                if (it is PlayerEvent.Shuffle) {
                    _enabled.value = it.shuffle
                }
            }
            .launchIn(viewModelScope)
    }

    init {
        listen()
    }

    operator fun invoke(shuffle: Boolean) {
        playerDispatcher.shuffle(shuffle)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
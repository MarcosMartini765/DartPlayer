package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerEvent
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    playerEventsDispatcher: PlayerEventsDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(false)

    val state: State<Boolean> = _state

    init {
        playerEventsDispatcher.sessionChangedListener
            .onEach {
                if (it is PlayerEvent.PlayerSessionChanged) {
                    _state.value = it.value
                }
            }
            .launchIn(viewModelScope)
    }
}
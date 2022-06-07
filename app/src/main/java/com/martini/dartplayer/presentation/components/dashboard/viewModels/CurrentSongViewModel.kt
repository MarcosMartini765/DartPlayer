package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerEvent
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.domain.usecases.song.CurrentSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrentSongViewModel @Inject constructor(
    private val playerEventsDispatcher: PlayerEventsDispatcher,
    private val currentSongUseCase: CurrentSongUseCase
) : ViewModel() {

    private val _state = mutableStateOf<CurrentSongState>(CurrentSongState.Initial)

    val state: State<CurrentSongState> = _state

    init {
        listen()
    }

    private fun listen() {
        playerEventsDispatcher.mediaTransitionListener
            .onEach {
                if (it is PlayerEvent.PlayerMediaTransition) {
                    getSong(it.id)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getSong(id: Long) {
        currentSongUseCase(id)
            .flowOn(Dispatchers.IO)
            .onEach {
                _state.value = it
            }
            .launchIn(viewModelScope)
    }
}
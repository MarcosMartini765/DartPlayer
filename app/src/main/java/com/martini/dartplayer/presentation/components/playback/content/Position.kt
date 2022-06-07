package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.CurrentSongViewModel
import com.martini.dartplayer.presentation.components.playback.viewModels.PlayerPositionViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlaybackPositionSlider(
    currentSongViewModel: CurrentSongViewModel = hiltViewModel(),
    playerPositionViewModel: PlayerPositionViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var timer: Job? = null

    var dragging by remember {
        mutableStateOf(false)
    }

    var position by remember {
        mutableStateOf(0F)
    }

    fun onChange(newPosition: Float) {
        dragging = true
        position = newPosition
        timer?.cancel()
        timer = null
    }

    fun createTimer(
    ) = scope.launch {
        delay(1000)
        dragging = false
    }

    fun changeFinished(duration: Long) {
        playerPositionViewModel(position * duration)
        if (timer == null) {
            timer = createTimer()
        }
    }

    fun getPosition(duration: Long): Float {
        return if (duration > 0) {
            playerPositionViewModel.state.value / duration
        } else {
            0F
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            timer?.cancel()
            timer = null
        }
    }

    when (val state = currentSongViewModel.state.value) {
        is CurrentSongState.Loaded -> {
            val duration = state.song.duration


            Slider(
                value = if (dragging) position else getPosition(duration),
                onValueChange = { onChange(it) },
                onValueChangeFinished = {
                    if (duration > 0) {
                        changeFinished(duration)
                    }
                }
            )
        }
        else -> {
            Slider(
                value = 0F,
                onValueChange = { },
                enabled = false
            )
        }
    }
}
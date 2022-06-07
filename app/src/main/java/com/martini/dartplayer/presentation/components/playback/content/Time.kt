package com.martini.dartplayer.presentation.components.playback.content

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.CurrentSongViewModel
import com.martini.dartplayer.presentation.components.playback.viewModels.PlayerPositionViewModel
import java.time.Duration

@Composable
fun PlaybackTime(
    currentSongViewModel: CurrentSongViewModel = hiltViewModel(),
    positionViewModel: PlayerPositionViewModel = hiltViewModel()
) {

    fun getDuration(duration: Long): String {
        return DateUtils.formatElapsedTime(Duration.ofMillis(duration).seconds)
    }

    fun getCurrentPosition(): String {
        val pos = positionViewModel.state.value.toLong()
        return DateUtils.formatElapsedTime(Duration.ofMillis(pos).seconds)
    }

    when (val state = currentSongViewModel.state.value) {
        is CurrentSongState.Loaded -> {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(getCurrentPosition())
                Text(getDuration(state.song.duration))
            }
        }
        else -> {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("00:00")
                Text("00:00")
            }
        }
    }
}
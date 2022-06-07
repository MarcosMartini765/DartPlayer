package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlayOrPauseViewModel

@Composable
fun PlaybackPlayOrPause(
    playOrPauseViewModel: PlayOrPauseViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
        shape = RoundedCornerShape(50),
        modifier = Modifier.size(70.dp)
    ) {
        if (playOrPauseViewModel.state.value) {
            IconButton(
                onClick = { playOrPauseViewModel() },
            ) {
                Icon(
                    Icons.Filled.Pause,
                    stringResource(id = R.string.play),
                    tint = if (MaterialTheme.colors.isLight) {
                        Color.Black
                    } else {
                        Color.White
                    }
                )
            }
        } else {
            IconButton(
                onClick = { playOrPauseViewModel() },
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    stringResource(id = R.string.play),
                    tint = if (MaterialTheme.colors.isLight) {
                        Color.Black
                    } else {
                        Color.White
                    }
                )
            }
        }
    }
}
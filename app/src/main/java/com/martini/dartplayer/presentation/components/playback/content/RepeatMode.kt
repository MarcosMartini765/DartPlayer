package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.player.PlaybackMode
import com.martini.dartplayer.presentation.components.playback.viewModels.PlayerRepeatModeViewModel

@Composable
fun PlaybackRepeatMode(
    playerRepeatModeViewModel: PlayerRepeatModeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val description = stringResource(id = R.string.exo_download_removing)

    val modifier = Modifier.size(PlaybackConstants.iconSize)

    val icon = when (playerRepeatModeViewModel.state.value) {
        PlaybackMode.OneTime -> {
            Icons.Filled.RepeatOne
        }
        PlaybackMode.Loop -> {
            Icons.Filled.Repeat
        }
        PlaybackMode.Repeat -> {
            Icons.Filled.Repeat
        }
    }

    val color = if (playerRepeatModeViewModel.state.value == PlaybackMode.Repeat) {
        Color.Gray
    } else {
        MaterialTheme.colors.secondary
    }

    Surface(
        color = color,
        elevation = 8.dp,
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                playSoundOnTap(context)
                playerRepeatModeViewModel(playerRepeatModeViewModel.state.value)
            }) {
            Icon(
                icon,
                description,
                tint = if (MaterialTheme.colors.isLight) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        }
    }
}
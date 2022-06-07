package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PreviousSongViewModel

@Composable
fun PlaybackBackButton(
    previousSongViewModel: PreviousSongViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val hasPrevious = previousSongViewModel.state.value

    val color = if (hasPrevious) {
        MaterialTheme.colors.secondary
    } else {
        Color.Gray
    }

    Surface(
        color = color,
        elevation = 8.dp,
        shape = RoundedCornerShape(50),
        modifier = Modifier.size(PlaybackConstants.iconSize)
    ) {
        IconButton(onClick = {
            playSoundOnTap(context)
            if (hasPrevious) {
                previousSongViewModel()
            }
        }) {
            Icon(
                Icons.Filled.ArrowBack,
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
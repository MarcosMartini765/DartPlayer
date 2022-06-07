package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.presentation.components.dashboard.viewModels.NextSongViewModel

@Composable
fun PlaybackNextButton(
    nextSongViewModel: NextSongViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val hasNext = nextSongViewModel.state.value

    val modifier = Modifier.size(PlaybackConstants.iconSize)

    val color = if (hasNext) {
        MaterialTheme.colors.secondary
    } else {
        Color.Gray
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
                if (hasNext) {
                    nextSongViewModel()
                }
            }) {
            Icon(
                Icons.Filled.ArrowForward,
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
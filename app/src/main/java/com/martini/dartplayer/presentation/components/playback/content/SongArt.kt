package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.CurrentSongViewModel
import java.io.File

@Composable
fun PlaybackSongArt(
    currentSongViewModel: CurrentSongViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val size = 220.dp
    val contentDescription = stringResource(R.string.album)
    val modifier = Modifier.size(size).clip(CircleShape)

    Box(
        modifier = modifier
    ) {
        when (val state = currentSongViewModel.state.value) {
            is CurrentSongState.Loaded -> {
                state.song.imageUri?.let {
                    val image = File(context.filesDir, state.song.id.toString())
                    Image(
                        painter = rememberImagePainter(data = image, builder = { crossfade(true) }),
                        contentDescription = contentDescription,
                        modifier = modifier
                    )
                } ?: Icon(Icons.Filled.Album, contentDescription)
            }
            is CurrentSongState.Failure -> {
                Icon(Icons.Filled.Album, contentDescription)
            }
            else -> {
                CircularProgressIndicator(modifier)
            }
        }
    }
}
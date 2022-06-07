package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun DashboardBottomAppBarSongArt(
    currentSongViewModel: CurrentSongViewModel = hiltViewModel()
) {

    val modifier = Modifier.size(60.dp)
    val description = stringResource(R.string.album)

    when (val state = currentSongViewModel.state.value) {
        is CurrentSongState.Loaded -> {
            val song = state.song

            song.imageUri?.let {
                val image = File(LocalContext.current.filesDir, song.id.toString())
                Image(
                    painter = rememberImagePainter(data = image, builder = { crossfade(true) }),
                    contentDescription = description,
                    modifier = modifier
                )
            } ?: Icon(
                Icons.Filled.Album,
                contentDescription = description,
                modifier = modifier
            )
        }
        is CurrentSongState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
        else -> {
            Icon(
                Icons.Filled.Album,
                contentDescription = description,
                modifier = modifier
            )
        }
    }
}
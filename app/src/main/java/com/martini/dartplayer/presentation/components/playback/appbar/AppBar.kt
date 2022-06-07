package com.martini.dartplayer.presentation.components.playback.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@Composable
fun PlaybackAppBar(
    popBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.NowPlaying))
        },
        navigationIcon = {
            IconButton(onClick = { popBack() }) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back))
            }
        }
    )
}
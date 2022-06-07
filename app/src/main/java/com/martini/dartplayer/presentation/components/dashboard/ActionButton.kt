package com.martini.dartplayer.presentation.components.dashboard

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.ShuffleSongsViewModel

@Composable
fun DashboardActionButton(
    shuffleSongs: ShuffleSongsViewModel = hiltViewModel(),
    loadMusic: LoadMusicViewModel = hiltViewModel()
) {
    val value = loadMusic.state.value

    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            playSoundOnTap(context)
            if (value is LoadSongsState.Loaded) {
                shuffleSongs(value.music.songs)
            }
        },
        backgroundColor = MaterialTheme.colors.secondary,
        content = {
            Icon(
                Icons.Filled.Shuffle,
                contentDescription = stringResource(R.string.Shuffle),
                tint = if (MaterialTheme.colors.isLight) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        },
    )
}
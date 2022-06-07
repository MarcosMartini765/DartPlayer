package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.dashboard.viewModels.NextSongViewModel

@Composable
fun DashboardAppBarNextButton(
    nextSongViewModel: NextSongViewModel = hiltViewModel()
) {

    if (nextSongViewModel.state.value) {
        IconButton(onClick = { nextSongViewModel() }) {
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = stringResource(R.string.NextSong),
            )
        }
    } else {
        IconButton(onClick = { }) {
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = stringResource(R.string.NextSong),
                tint = Color.Gray
            )
        }
    }
}
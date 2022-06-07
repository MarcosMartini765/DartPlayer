package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlayOrPauseViewModel

@Composable
fun DashboardAppBarPlayOrPause(
    playOrPauseViewModel: PlayOrPauseViewModel = hiltViewModel()
) {

    if (playOrPauseViewModel.state.value) {
        IconButton(onClick = { playOrPauseViewModel() }) {
            Icon(Icons.Filled.Pause, contentDescription = stringResource(R.string.Pause))
        }
    } else {
        IconButton(onClick = { playOrPauseViewModel() }) {
            Icon(Icons.Filled.PlayArrow, contentDescription = stringResource(R.string.Resume))
        }
    }
}
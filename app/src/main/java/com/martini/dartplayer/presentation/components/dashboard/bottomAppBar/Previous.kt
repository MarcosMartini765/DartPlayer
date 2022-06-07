package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PreviousSongViewModel

@Composable
fun DashboardAppBarPreviousButton(
    previousSongViewModel: PreviousSongViewModel = hiltViewModel()
) {

    if (previousSongViewModel.state.value) {
        IconButton(onClick = { previousSongViewModel() }) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.PreviousSong),
            )
        }
    } else {
        IconButton(onClick = { }) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.PreviousSong),
                tint = Color.Gray
            )
        }
    }
}
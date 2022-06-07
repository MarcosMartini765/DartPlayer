package com.martini.dartplayer.presentation.components.dashboard.bottomAppBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.CurrentSongViewModel

@Composable
fun DashboardBottomAppBarSongText(
    currentSongViewModel: CurrentSongViewModel = hiltViewModel()
) {

    val modifier = Modifier.size(50.dp)

    when (val state = currentSongViewModel.state.value) {
        is CurrentSongState.Loaded -> {
            val song = state.song

            Column {
                Text(
                    song.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    song.artist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        is CurrentSongState.Failure -> {
            Column {
                Text(stringResource(R.string.Error), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(stringResource(R.string.Error), maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
        else -> CircularProgressIndicator(
            modifier = modifier
        )
    }
}
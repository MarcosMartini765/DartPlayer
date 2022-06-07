package com.martini.dartplayer.presentation.components.playback.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.CurrentSongViewModel

@Composable
fun PlaybackSongText(
    currentSongViewModel: CurrentSongViewModel = hiltViewModel()
) {
    when (val state = currentSongViewModel.state.value) {
        is CurrentSongState.Loaded -> {
            Column {
                Text(
                    state.song.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    state.song.artist,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                )
            }
        }
        is CurrentSongState.Failure -> {
            Text(stringResource(id = R.string.Error))
        }
        else -> {
            Column {
                LinearProgressIndicator()
                Spacer(modifier = Modifier.size(8.dp))
                LinearProgressIndicator()
            }
        }
    }
}
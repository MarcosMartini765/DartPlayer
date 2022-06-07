package com.martini.dartplayer.presentation.components.playlist.appBar

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.GetPlaylistViewModel

@Composable
fun PlaylistAppBarTitle(
    getPlaylistViewModel: GetPlaylistViewModel = hiltViewModel(),
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel()
) {
    when (val state = getPlaylistViewModel.state.value) {
        is GetPlaylistState.Loaded -> {
            val selected = selectedMusicViewModel.state.value.music
            if (selected.isEmpty()) {
                Text(
                    text = state.playlistWithSongs.playlist.playlistName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            } else {
                Text(text = "${selected.count()}")
            }
        }
        is GetPlaylistState.Failure -> {
            val text = stringResource(id = R.string.Error)
            Text(text = text)
        }
        else -> CircularProgressIndicator()
    }
}
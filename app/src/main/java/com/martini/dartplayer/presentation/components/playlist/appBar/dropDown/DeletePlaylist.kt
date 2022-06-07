package com.martini.dartplayer.presentation.components.playlist.appBar.dropDown

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.DeletePlaylistViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.GetPlaylistViewModel

@Composable
fun PlaylistAppBarDeletePlaylist(
    deletePlaylistViewModel: DeletePlaylistViewModel = hiltViewModel(),
    onClick: () -> Unit,
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel(),
    getPlaylistViewModel: GetPlaylistViewModel = hiltViewModel()
) {

    if (selectedMusicViewModel.state.value.music.isNotEmpty()) return

    val description = stringResource(id = R.string.DeletePlaylist)

    fun remove() {
        when(val state = getPlaylistViewModel.state.value) {
            is GetPlaylistState.Loaded -> {
                deletePlaylistViewModel(state.playlistWithSongs)
                onClick()
            }
            else -> {}
        }
    }

    DropdownMenuItem(onClick = { remove() }) {
        Text(text = description)
    }
}
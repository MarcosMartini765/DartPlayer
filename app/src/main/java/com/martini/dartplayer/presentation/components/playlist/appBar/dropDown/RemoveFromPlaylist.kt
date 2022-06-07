package com.martini.dartplayer.presentation.components.playlist.appBar.dropDown

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.GetPlaylistViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.RemoveSongFromPlaylistVIewModel

@Composable
fun PlaylistDropDownRemoveFromPlaylist(
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel(),
    removeSongFromPlaylistVIewModel: RemoveSongFromPlaylistVIewModel = hiltViewModel(),
    onClick: () -> Unit,
    getPlaylistViewModel: GetPlaylistViewModel = hiltViewModel()
) {

    if (selectedMusicViewModel.state.value.music.isEmpty()) return

    val description = stringResource(id = R.string.RemoveFromPlaylist)

    fun remove() {
        when (val state = getPlaylistViewModel.state.value) {
            is GetPlaylistState.Loaded -> {
                removeSongFromPlaylistVIewModel(
                    selectedMusicViewModel.state.value,
                    state.playlistWithSongs
                )
                onClick()
            }
            else -> {}
        }
    }

    DropdownMenuItem(onClick = { remove() }) {
        Text(text = description)
    }
}
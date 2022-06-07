package com.martini.dartplayer.presentation.components.playlist.content

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.presentation.components.playlist.viewModels.GetPlaylistViewModel

@ExperimentalMaterialApi
@Composable
fun PlaylistContent(
    getPlaylistViewModel: GetPlaylistViewModel = hiltViewModel(),
    onMenuClick: () -> Unit
) {
    when (val state = getPlaylistViewModel.state.value) {
        is GetPlaylistState.Loaded -> PlaylistContentLoaded(
            playlistWithSongs = state.playlistWithSongs,
            onMenuClick = onMenuClick
        )
        is GetPlaylistState.Failure -> PlaylistContentFailure()
        else -> PlaylistContentLoading()
    }
}
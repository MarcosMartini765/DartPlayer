package com.martini.dartplayer.presentation.components.dashboard.playlists.content

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.presentation.components.dashboard.LoadMusicViewModel

@ExperimentalMaterialApi
@Composable
fun DashboardPlaylist(
    loadMusicViewModel: LoadMusicViewModel = hiltViewModel(),
    navigateToPlaylist: (playlist: PlaylistWithSongs) -> Unit,
    onMenuClick: () -> Unit
) {
    when (val state = loadMusicViewModel.state.value) {
        is LoadSongsState.Loaded -> PlaylistContentLoaded(
            playlists = state.music.playlists,
            navigateToPlaylist = navigateToPlaylist,
            onMenuClick = onMenuClick
        )
        is LoadSongsState.Failure -> PlaylistContentFailure()
        else -> PlaylistContentLoading()
    }
}
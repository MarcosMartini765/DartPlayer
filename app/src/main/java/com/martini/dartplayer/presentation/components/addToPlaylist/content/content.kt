package com.martini.dartplayer.presentation.components.addToPlaylist.content

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.presentation.components.dashboard.LoadMusicViewModel

@ExperimentalMaterialApi
@Composable
fun AddToPlaylistContent(
    loadMusicViewModel: LoadMusicViewModel = hiltViewModel()
) {
    when (val state = loadMusicViewModel.state.value) {
        is LoadSongsState.Loaded -> AddToPlaylistContentLoaded(
            playlists = state.music.playlists
        )
        is LoadSongsState.Failure -> AddToPlaylistContentFailure()
        else -> AddToPlaylistContentLoading()
    }
}
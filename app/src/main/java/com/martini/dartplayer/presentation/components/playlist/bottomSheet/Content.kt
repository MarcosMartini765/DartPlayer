package com.martini.dartplayer.presentation.components.playlist.bottomSheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.usecases.song.GetSongInfoState
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel

@ExperimentalMaterialApi
@Composable
fun PlaylistBottomSheetContent(
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    when (val state = getSongInfoViewModel.state.value) {
        is GetSongInfoState.LoadedSong -> PlaylistBottomSheetContentLoaded(
            song = state.song,
            onClick = onClick
        )
        is GetSongInfoState.Loading -> PlaylistBottomSheetContentLoading()
        is GetSongInfoState.Initial -> PlaylistBottomSheetContentLoading()
        else -> PlaylistBottomSheetContentFailure()
    }
}
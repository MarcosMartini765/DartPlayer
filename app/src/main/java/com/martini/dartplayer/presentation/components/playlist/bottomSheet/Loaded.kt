package com.martini.dartplayer.presentation.components.playlist.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayNextSongComponent
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlaySongComponent
import com.martini.dartplayer.presentation.components.playlist.viewModels.GetPlaylistViewModel

@ExperimentalMaterialApi
@Composable
fun PlaylistBottomSheetContentLoaded(
    song: Song,
    onClick: () -> Unit,
    getPlaylistViewModel: GetPlaylistViewModel = hiltViewModel()
) {
    Column {
        when (val state = getPlaylistViewModel.state.value) {
            is GetPlaylistState.Loaded -> {
                PlaySongComponent(
                    songs = state.playlistWithSongs.songs,
                    song = song,
                    onClick = onClick
                )
            }
            else -> {}
        }
        PlayNextSongComponent(
            song = song,
            onClick = onClick
        )
        when (val state = getPlaylistViewModel.state.value) {
            is GetPlaylistState.Loaded -> {
                RemoveSongFromPlaylist(
                    song = song,
                    onClick = onClick,
                    playlistWithSongs = state.playlistWithSongs
                )
            }
            else -> {

            }
        }
        PlaylistBottomSheetDeleteSong(
            song = song,
            onClick = onClick
        )
    }
}
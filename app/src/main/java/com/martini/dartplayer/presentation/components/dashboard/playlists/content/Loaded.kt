package com.martini.dartplayer.presentation.components.dashboard.playlists.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.playlists.content.tile.PlaylistTile
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel

@ExperimentalMaterialApi
@Composable
fun PlaylistContentLoaded(
    playlists: List<PlaylistWithSongs>,
    selectedViewModel: SelectedMusicViewModel = hiltViewModel(),
    navigateToPlaylist: (playlist: PlaylistWithSongs) -> Unit,
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    onMenuClick: () -> Unit,
) {

    if (playlists.isEmpty()) {
        return PlaylistEmpty()
    }

    val selected = selectedViewModel.state.value.music

    fun onTap(playlistWithSongs: PlaylistWithSongs) {
        if (selectedViewModel.state.value.music.isEmpty()) {
            navigateToPlaylist(playlistWithSongs)
        } else {
            selectedViewModel.addOrRemovePlaylist(playlistWithSongs)
        }
    }

    fun onLongPress(playlistWithSongs: PlaylistWithSongs) {
        selectedViewModel.addOrRemovePlaylist(playlistWithSongs)
    }

    LazyColumn {
        items(
            items = playlists,
            key = { it.playlist.playlistName }
        ) { playlist ->
            PlaylistTile(
                playlist = playlist,
                selected = selected.contains(playlist),
                onTap = { onTap(it) },
                onLongPress = { onLongPress(it) },
                onMenuClick = {
                    getSongInfoViewModel.getPlaylistInfo(playlist)
                    onMenuClick()
                }
            )
        }
    }
}
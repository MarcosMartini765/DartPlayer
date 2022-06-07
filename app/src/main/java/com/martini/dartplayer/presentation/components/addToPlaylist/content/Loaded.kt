package com.martini.dartplayer.presentation.components.addToPlaylist.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.params.AddToPlaylistParams
import com.martini.dartplayer.presentation.components.AddToPlaylistViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.RemoveSongFromPlaylistVIewModel

@ExperimentalMaterialApi
@Composable
fun AddToPlaylistContentLoaded(
    playlists: List<PlaylistWithSongs>,
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel(),
    addToPlaylistViewModel: AddToPlaylistViewModel = hiltViewModel(),
    removeSongFromPlaylistVIewModel: RemoveSongFromPlaylistVIewModel = hiltViewModel()
) {

    fun onChanged(shouldAdd: Boolean, playlist: PlaylistWithSongs) {
        if (shouldAdd) {
            addToPlaylistViewModel(
                AddToPlaylistParams(
                    selected = selectedMusicViewModel.state.value,
                    playlist = playlist.playlist
                )
            )
            return
        }
        removeSongFromPlaylistVIewModel(
            selected = selectedMusicViewModel.state.value,
            playlist = playlist
        )
    }

    LazyColumn {
        items(
            items = playlists,
            key = { it.playlist.playlistName }
        ) { playlist ->
            AddToPlaylistContentItem(
                playlistWithSongs = playlist,
                contains = playlist.songs.containsAll(selectedMusicViewModel.state.value.music),
                onClick = { onChanged(it, playlist) }
            )
        }
    }
}
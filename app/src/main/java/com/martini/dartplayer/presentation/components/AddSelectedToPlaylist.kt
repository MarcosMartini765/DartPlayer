package com.martini.dartplayer.presentation.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.AddToPlaylistButton

@ExperimentalMaterialApi
@Composable
fun AddSelectedToPlaylist(
    selectedViewModel: SelectedMusicViewModel = hiltViewModel(),
) {

    val selected = selectedViewModel.state.value

    if (selected.music.isEmpty()) return

    if (selected.music.filterIsInstance<PlaylistWithSongs>().isNotEmpty()) return

    AddToPlaylistButton(onClick = {})
}
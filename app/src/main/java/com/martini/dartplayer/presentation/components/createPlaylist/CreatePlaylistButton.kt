package com.martini.dartplayer.presentation.components.createPlaylist

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.presentation.components.CreatePlaylistViewModel

@Composable
fun CreatePlaylistButton(
    text: String,
    createPlaylistViewModel: CreatePlaylistViewModel = hiltViewModel()
) {

    if (text.isEmpty()) return
    if (text.trim().isEmpty()) return
    if (text.trim().length < 2) return

    fun create() {
        createPlaylistViewModel(text)
    }

    IconButton(onClick = { create() }) {
        Icon(Icons.Filled.Create, contentDescription = "Create")
    }
}
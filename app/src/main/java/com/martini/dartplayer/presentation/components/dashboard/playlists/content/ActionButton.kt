package com.martini.dartplayer.presentation.components.dashboard.playlists.content

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable

@Composable
fun PlaylistsActionButton() {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Create, contentDescription = "Create")
    }
}
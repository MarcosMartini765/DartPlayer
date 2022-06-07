package com.martini.dartplayer.presentation.components.dashboard.playlists.content

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlaylistContentLoading() {
    CircularProgressIndicator(
        Modifier.fillMaxSize(0.4f)
    )
}
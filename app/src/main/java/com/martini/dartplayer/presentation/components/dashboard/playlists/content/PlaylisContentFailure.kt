package com.martini.dartplayer.presentation.components.dashboard.playlists.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaylistContentFailure() {
    Column {
        Text("Something went wrong!")
        Spacer(modifier = Modifier.size(16.dp))
        Icon(Icons.Filled.Error, contentDescription = "Error")
    }
}
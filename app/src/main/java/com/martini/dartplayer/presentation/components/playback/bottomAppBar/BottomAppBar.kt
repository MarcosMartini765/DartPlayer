package com.martini.dartplayer.presentation.components.playback.bottomAppBar

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun PlaybackBottomAppBar(
    onClick: () -> Unit
) {
    BottomAppBar {
        TextButton(onClick = { onClick() }) {
            Text("Up next", style = TextStyle(color = Color.White))
        }
    }
}
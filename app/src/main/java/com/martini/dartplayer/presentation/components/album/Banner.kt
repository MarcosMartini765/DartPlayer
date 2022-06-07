package com.martini.dartplayer.presentation.components.album

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlbumBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AlbumImage()
            Spacer(modifier = Modifier.width(16.dp))
            AlbumInfo()
        }
    }
}
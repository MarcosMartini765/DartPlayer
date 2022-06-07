package com.martini.dartplayer.presentation.components.albumForArtist.content

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlbumForArtistBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AlbumForArtistAlbumImage()
            Spacer(modifier = Modifier.width(16.dp))
            AlbumForArtistInfo()
        }
    }
}
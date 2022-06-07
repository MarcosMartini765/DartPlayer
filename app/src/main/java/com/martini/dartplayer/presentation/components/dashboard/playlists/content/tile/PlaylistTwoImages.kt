package com.martini.dartplayer.presentation.components.dashboard.playlists.content.tile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import java.io.File

@Composable
fun PlaylistTwoImage(
    images: List<File>,
    playlistWithSongs: PlaylistWithSongs
) {
    Row(
        Modifier.size(80.dp),
        verticalAlignment = Alignment.Top
    ) {
        images.map { file ->
            Image(
                painter = rememberImagePainter(file),
                contentDescription = playlistWithSongs.playlist.playlistName,
                modifier = Modifier.size(40.dp)
            )
        }.take(2)
    }
}
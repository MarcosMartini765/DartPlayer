package com.martini.dartplayer.presentation.components.dashboard.playlists.content.tile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import java.io.File

@Composable
fun PlaylistOneImage(
    images: List<File>,
    playlistWithSongs: PlaylistWithSongs
) {
    Box(Modifier.size(80.dp)) {
        images.map { file ->
            Box(Modifier.align(Alignment.Center)) {
                Image(
                    painter = rememberImagePainter(file),
                    contentDescription = playlistWithSongs.playlist.playlistName,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}
package com.martini.dartplayer.presentation.components.dashboard.playlists.content.tile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FeaturedPlayList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import java.io.File

@Composable
fun PlaylistArt(
    playlist: PlaylistWithSongs
) {

    val smallSize = 40.dp
    val bigSize = 80.dp
    val modifier = Modifier.size(bigSize)
    val context = LocalContext.current

    val images = playlist.songs.filter { it.imageUri is String }.map {
        File(context.filesDir, it.id.toString())
    }

    val alignments = listOf(
        Alignment.TopStart,
        Alignment.TopEnd,
        Alignment.BottomStart,
        Alignment.BottomEnd,
    )

    if (images.isEmpty()) {
        return Icon(
            Icons.Filled.FeaturedPlayList,
            contentDescription = playlist.playlist.playlistName,
            modifier = modifier
        )
    }

    when(images.count()) {
        1 -> { PlaylistOneImage(images = images, playlistWithSongs = playlist) }
        2 -> { PlaylistTwoImage(images = images, playlistWithSongs = playlist) }
        else -> {
            Box(
                Modifier.size(bigSize),
                contentAlignment = Alignment.Center
            ) {
                images.mapIndexed { index, file ->
                    Box(Modifier.align(alignments[index])) {
                        Image(
                            painter = rememberImagePainter(file),
                            contentDescription = playlist.playlist.playlistName,
                            modifier = Modifier.size(smallSize)
                        )
                    }
                }.take(4)
            }
        }
    }
}
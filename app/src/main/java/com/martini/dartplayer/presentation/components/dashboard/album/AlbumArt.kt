package com.martini.dartplayer.presentation.components.dashboard.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import java.io.File

@Composable
fun AlbumArt(albumWithSongs: AlbumWithSongs) {

    val imageSize = 48.dp
    val modifier = Modifier.size(imageSize)
    val contentDescription = stringResource(R.string.album)

    albumWithSongs.songs.firstOrNull { it.imageUri != null }?.let {
        val image = File(LocalContext.current.filesDir, it.id.toString())
        Image(
            painter = rememberImagePainter(image,
                builder = { crossfade(true) }),
            contentDescription = contentDescription,
            modifier = modifier
        )
    } ?: Icon(
        Icons.Filled.Album, contentDescription = contentDescription,
        modifier = modifier
    )
}
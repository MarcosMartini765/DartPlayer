package com.martini.dartplayer.presentation.components.dashboard.song

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.Song
import java.io.File

@Composable
fun SongArt(song: Song) {

    val imageSize = 48.dp
    val modifier = Modifier.size(imageSize)
    val contentDescription = stringResource(R.string.album)

    song.imageUri?.let {
        val image = File(LocalContext.current.filesDir, song.id.toString())
        Image(
            painter = rememberImagePainter(data = image, builder = { crossfade(true) }),
            contentDescription = contentDescription,
            modifier = modifier
        )
    } ?: Icon(
        Icons.Filled.Audiotrack, contentDescription = contentDescription,
        modifier = modifier
    )
}
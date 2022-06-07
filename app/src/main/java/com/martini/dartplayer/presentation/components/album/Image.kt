package com.martini.dartplayer.presentation.components.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import java.io.File

@Composable
fun AlbumImage(
    model: LoadAlbumViewModel = hiltViewModel()
) {

    val contentDescription = stringResource(R.string.album)
    val modifier = Modifier.size(100.dp)

    when (val state = model.state.value) {
        is LoadAlbumState.Loaded -> {
            state.albumWithSongAndArtists.songs.songs.firstOrNull { it.imageUri != null }?.let {
                val image = File(LocalContext.current.filesDir, it.id.toString())
                Image(
                    painter = rememberImagePainter(image), contentDescription = contentDescription,
                    modifier = modifier
                )
            } ?: Icon(
                Icons.Filled.Album, contentDescription = contentDescription,
                modifier = modifier
            )
        }
        is LoadAlbumState.Loading -> {
            CircularProgressIndicator()
        }
        else -> Text(stringResource(R.string.Error))
    }
}
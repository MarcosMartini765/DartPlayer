package com.martini.dartplayer.presentation.components.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState

@Composable
fun AlbumInfo(
    model: LoadAlbumViewModel = hiltViewModel()
) {
    when (val state = model.state.value) {
        is LoadAlbumState.Loaded -> {
            val albumData = state.albumWithSongAndArtists
            val name = albumData.artists.album.albumName
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    name, maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    albumData.artists.artists.joinToString(", ") { it.artistName },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    "${albumData.songs.songs.size} ${stringResource(R.string.Songs)}(s)",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.overline
                )
            }
        }
        else -> {}
    }
}
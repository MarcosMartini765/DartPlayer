package com.martini.dartplayer.presentation.components.albumForArtist.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistState
import com.martini.dartplayer.presentation.components.albumForArtist.viewModels.LoadAlbumForArtistViewModel

@Composable
fun AlbumForArtistInfo(
    loadAlbumForArtist: LoadAlbumForArtistViewModel = hiltViewModel()
) {
    when (val state = loadAlbumForArtist.state.value) {
        is LoadAlbumForArtistState.Loaded -> {
            val data = state.albumWithSongs
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    data.album.albumName, maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    "${data.songs.count()} ${stringResource(R.string.song)}(s)",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.overline
                )
            }
        }
        is LoadAlbumForArtistState.Loading -> {
            CircularProgressIndicator()
        }
        else -> Text(stringResource(R.string.Error))
    }
}
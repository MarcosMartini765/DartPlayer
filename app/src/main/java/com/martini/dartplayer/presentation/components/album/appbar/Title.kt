package com.martini.dartplayer.presentation.components.album.appbar

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.album.LoadAlbumViewModel

@Composable
fun AlbumAppBarTitle(
    selected: SelectedMusicViewModel = hiltViewModel(),
    loadAlbumViewModel: LoadAlbumViewModel = hiltViewModel()
) {
    when {
        selected.state.value.music.isNotEmpty() -> {
            Text(selected.state.value.music.count().toString())
        }
        else -> {
            when (val state = loadAlbumViewModel.state.value) {
                is LoadAlbumState.Loaded -> {
                    Text(
                        state.albumWithSongAndArtists.artists.album.albumName,
                        overflow = TextOverflow.Ellipsis, maxLines = 1
                    )
                }
                is LoadAlbumState.Failure -> {
                    Text(stringResource(R.string.Error))
                }
                else -> CircularProgressIndicator()
            }
        }
    }
}
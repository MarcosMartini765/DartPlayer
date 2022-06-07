package com.martini.dartplayer.presentation.components.albumForArtist.appbar

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.albumForArtist.viewModels.LoadAlbumForArtistViewModel

@Composable
fun AlbumForArtistAppBarTitle(
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    loadAlbumForArtist: LoadAlbumForArtistViewModel = hiltViewModel()
) {
    val selected = selectedMusic.state.value.music

    if (selected.isNotEmpty()) {
        Text("${selected.count()}")
    } else {
        when (val state = loadAlbumForArtist.state.value) {
            is LoadAlbumForArtistState.Loaded -> {
                Text(
                    state.albumWithSongs.album.albumName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            is LoadAlbumForArtistState.Failure -> Text(stringResource(R.string.Error))
            else -> CircularProgressIndicator()
        }
    }
}
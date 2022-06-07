package com.martini.dartplayer.presentation.components.album.appbar

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.album.LoadAlbumViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.ShuffleSongsViewModel

@Composable
fun AlbumActionButton(
    loadAlbumViewModel: LoadAlbumViewModel = hiltViewModel(),
    shuffleSongsViewModel: ShuffleSongsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    fun shuffle() {
        playSoundOnTap(context)
        when (val state = loadAlbumViewModel.state.value) {
            is LoadAlbumState.Loaded -> {
                shuffleSongsViewModel(state.albumWithSongAndArtists.songs.songs)
            }
            else -> {}
        }
    }

    FloatingActionButton(onClick = { shuffle() }) {
        Icon(
            Icons.Filled.Shuffle,
            contentDescription = stringResource(R.string.shuffleAlbums),
            tint = if (MaterialTheme.colors.isLight) {
                Color.Black
            } else {
                Color.White
            }
        )
    }
}
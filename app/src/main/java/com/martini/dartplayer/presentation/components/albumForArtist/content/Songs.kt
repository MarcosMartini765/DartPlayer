package com.martini.dartplayer.presentation.components.albumForArtist.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistState
import com.martini.dartplayer.presentation.components.albumForArtist.viewModels.LoadAlbumForArtistViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistSongs(
    loadAlbumForArtist: LoadAlbumForArtistViewModel = hiltViewModel(),
    onMenuClick: (song: Song) -> Unit
) {
    when (val state = loadAlbumForArtist.state.value) {
        is LoadAlbumForArtistState.Loaded -> {
            val data = state.albumWithSongs
            AlbumForArtistLazyColumn(
                songs = data.songs,
                onMenuClick = onMenuClick
            )
        }
        is LoadAlbumForArtistState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> Text(stringResource(R.string.Error))
    }
}
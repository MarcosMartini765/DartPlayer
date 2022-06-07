package com.martini.dartplayer.presentation.components.artist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.usecases.artist.LoadArtistState
import com.martini.dartplayer.presentation.components.NavigationViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.album.AlbumTile
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel

@ExperimentalMaterialApi
@Composable
fun ArtistAlbums(
    onMenuClick: () -> Unit,
    model: LoadArtistViewModel = hiltViewModel(),
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    getSongInfo: GetSongInfoViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {

    val selected = selectedMusic.state.value.music

    fun navigateToAlbum(albumName: String) {
        when (val state = model.state.value) {
            is LoadArtistState.Loaded -> {
                navigationViewModel.navigateToAlbumForArtist(
                    album = albumName,
                    artist = state.data.name
                )
            }
            else -> {}
        }
    }

    when (val state = model.state.value) {
        is LoadArtistState.Loaded -> {
            LazyColumn {
                items(
                    items = state.data.albums,
                    key = { it.album.albumName }
                ) { album ->
                    AlbumTile(
                        albumWithSongs = album,
                        onClick = {
                            getSongInfo.getAlbumInfo(album)
                            onMenuClick()
                        },
                        onTap = {
                            if (selected.isNotEmpty()) {
                                selectedMusic.addOrRemoveAlbum(it)
                            } else {
                                navigateToAlbum(it.album.albumName)
                            }
                        },
                        onLongPress = {
                            selectedMusic.addOrRemoveAlbum(it)
                        },
                        selected = selected.contains(album)
                    )
                }
            }
        }
        is LoadArtistState.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.Error))
            }
        }
        else -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
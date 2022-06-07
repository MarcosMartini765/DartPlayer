package com.martini.dartplayer.presentation.components.dashboard.album

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.presentation.components.NavigationViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumsLazyColumn(
    selected: SelectedMusicViewModel = hiltViewModel(),
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    albums: List<AlbumWithSongs>,
    onClick: () -> Unit,
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    LazyColumn {
        items(
            items = albums,
            key = { it.album.albumName }
        ) { album ->
            AlbumTile(
                album,
                onClick = {
                    getSongInfoViewModel.getAlbumInfo(it)
                    onClick()
                },
                onTap = {
                    if (selected.state.value.music.isNotEmpty()) {
                        selected.addOrRemoveAlbum(it)
                    } else {
                        navigationViewModel.navigateToAlbum(it.album.albumName)
                    }
                },
                onLongPress = {
                    selected.addOrRemoveAlbum(it)
                },
                selected = selected.state.value.music.contains(album)
            )
        }
    }
}
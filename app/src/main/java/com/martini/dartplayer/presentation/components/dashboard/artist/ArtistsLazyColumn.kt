package com.martini.dartplayer.presentation.components.dashboard.artist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel

@ExperimentalMaterialApi
@Composable
fun ArtistsLazyColumn(
    artists: List<ArtistWithAlbums>,
    selected: SelectedMusicViewModel = hiltViewModel(),
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    onArtistClick: () -> Unit,
    navigate: (artist: ArtistWithAlbums) -> Unit,
) {
    LazyColumn {
        items(
            items = artists,
            key = { it.artist.artistName }
        ) { artist ->
            ArtistTile(
                artist,
                onClick = {
                    getSongInfoViewModel.getArtistInfo(it)
                    onArtistClick()
                },
                onTap = {
                    if (selected.state.value.music.isNotEmpty()) {
                        selected.addOrRemoveArtist(it)
                    } else {
                        navigate(it)
                    }
                },
                onLongPress = { selected.addOrRemoveArtist(it) },
                selected = selected.state.value.music.contains(artist)
            )
        }
    }
}
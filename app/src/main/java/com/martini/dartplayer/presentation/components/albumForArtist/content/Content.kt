package com.martini.dartplayer.presentation.components.albumForArtist.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.martini.dartplayer.domain.entity.song.Song

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistContent(
    onMenuClick: (song: Song) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AlbumForArtistBanner()
        Divider()
        AlbumForArtistSongs(onMenuClick = onMenuClick)
    }
}
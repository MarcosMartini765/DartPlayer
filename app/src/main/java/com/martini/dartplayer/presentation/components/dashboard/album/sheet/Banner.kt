package com.martini.dartplayer.presentation.components.dashboard.album.sheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.presentation.components.dashboard.album.AlbumArt

@ExperimentalMaterialApi
@Composable
fun AlbumSheetBanner(
    album: AlbumWithSongAndArtists
) {
    ListItem(
        icon = { AlbumArt(album.songs) },
        text = {
            Text(
                album.songs.album.albumName, maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        secondaryText = {
            if (album.artists.artists.count() > 1) Text(stringResource(R.string.variousArtists))
            else Text(
                album.artists.artists.firstOrNull()?.artistName ?: "",
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
    )
}
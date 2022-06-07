package com.martini.dartplayer.presentation.components.dashboard.album.sheet

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.album.AlbumWithSongAndArtists
import com.martini.dartplayer.presentation.components.Chip

@Composable
fun AlbumSheetChips(
    album: AlbumWithSongAndArtists,
    goToArtist: (name: String) -> Unit
) {

    val state = rememberScrollState()

    if (album.artists.artists.count() > 1) {
        Column(
            Modifier.padding(16.dp)
        ) {
            Text("${stringResource(R.string.Artists)}: ")
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.horizontalScroll(state),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                album.artists.artists.forEach { artist ->
                    Chip(
                        text = artist.artistName,
                        icon = null,
                        onClick = { goToArtist(artist.artistName) })
                }
            }
            Divider()
        }
    }
}
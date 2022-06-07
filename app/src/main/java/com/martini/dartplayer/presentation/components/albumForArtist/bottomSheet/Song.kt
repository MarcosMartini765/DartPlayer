package com.martini.dartplayer.presentation.components.albumForArtist.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.AddToPlaylistButton
import com.martini.dartplayer.presentation.components.dashboard.song.SongArt

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistBottomSheetSong(
    song: Song,
    onClick: () -> Unit
) {
    Column {
        ListItem(
            icon = { SongArt(song) },
            text = { Text(song.name, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            secondaryText = { Text(song.artist) }
        )
        Divider()
        AlbumForArtistPlaySong(song = song, onClick = onClick)
        AlbumForArtistPlayNextSong(song = song, onClick = onClick)
        AddToPlaylistButton(onClick = {})
        AlbumForArtistBottomSheetDeleteSong(song = song, onClick = onClick)
    }
}
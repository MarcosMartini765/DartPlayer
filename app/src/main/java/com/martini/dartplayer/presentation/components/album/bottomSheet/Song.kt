package com.martini.dartplayer.presentation.components.album.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.AddToPlaylistButton
import com.martini.dartplayer.presentation.components.dashboard.song.SongArt

@ExperimentalMaterialApi
@Composable
fun AlbumBottomSheetSong(
    song: Song,
    onClick: () -> Unit,
    selectedMusicViewModel: SelectedMusicViewModel  = hiltViewModel()
) {
    Column {
        ListItem(
            icon = { SongArt(song) },
            text = { Text(song.name, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            secondaryText = { Text(song.artist) }
        )
        Divider()
        AlbumPlaySong(
            song = song,
            onClick = onClick
        )
        AlbumPlayNextSong(song = song, onClick = onClick)
        AddToPlaylistButton(
            onClick = { selectedMusicViewModel.addSong(song) }
        )
        AlbumBottomSheetDeleteSong(song = song, onClick = onClick)
    }
}
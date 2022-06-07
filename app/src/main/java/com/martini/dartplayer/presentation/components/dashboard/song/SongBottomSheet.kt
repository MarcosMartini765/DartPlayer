package com.martini.dartplayer.presentation.components.dashboard.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.presentation.components.NavigationViewModel
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.LoadMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.AddToPlaylistButton
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.DashboardBottomSheetDeleteSong
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlayNextSongComponent
import com.martini.dartplayer.presentation.components.dashboard.bottomSheet.PlaySongComponent

@ExperimentalMaterialApi
@Composable
fun SongBottomSheet(
    song: Song,
    onClick: () -> Unit,
    loadMusicViewModel: LoadMusicViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel()
) {

    fun getSongs(): List<Song> {
        return when (val state = loadMusicViewModel.state.value) {
            is LoadSongsState.Loaded -> state.music.songs
            else -> listOf()
        }
    }

    Column {
        ListItem(
            icon = { SongArt(song) },
            text = { Text(song.name, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            secondaryText = { Text(song.artist) }
        )
        Divider()
        PlaySongComponent(
            song = song,
            onClick = onClick,
            songs = getSongs()
        )
        PlayNextSongComponent(song = song, onClick = onClick)
        ListItem(
            modifier = Modifier.clickable { navigationViewModel.navigateToArtist(song.artist) },
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(R.string.artist)
                )
            },
            text = { Text(stringResource(R.string.goToArtist)) }
        )
        ListItem(
            modifier = Modifier.clickable { navigationViewModel.navigateToAlbum(song.album) },
            icon = {
                Icon(
                    Icons.Filled.Album,
                    contentDescription = stringResource(R.string.goToAlbum)
                )
            },
            text = { Text(stringResource(R.string.goToAlbum)) }
        )
        AddToPlaylistButton(
            onClick = { selectedMusicViewModel.addSong(song) }
        )
        DashboardBottomSheetDeleteSong(onClick = onClick, song = song)
    }
}
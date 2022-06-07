package com.martini.dartplayer.presentation.components.playlist.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.SongTile
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel

@ExperimentalMaterialApi
@Composable
fun PlaylistContentLoaded(
    playlistWithSongs: PlaylistWithSongs,
    selectedMusicViewModel: SelectedMusicViewModel = hiltViewModel(),
    playSongsAtIndexViewModel: PlaySongsAtIndexViewModel = hiltViewModel(),
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    onMenuClick: () -> Unit
) {

    val selected = selectedMusicViewModel.state.value.music

    if (playlistWithSongs.songs.isEmpty()) {
        PlaylistContentEmpty()
        return
    }

    fun play(song: Song) {
        playSongsAtIndexViewModel(
            PlaySongAtIndexParams(
                songs = playlistWithSongs.songs,
                index = playlistWithSongs.songs.indexOf(song),
                shuffled = false
            )
        )
    }

    fun onTap(song: Song) {
        if (selectedMusicViewModel.state.value.music.isEmpty()) {
            play(song)
            return
        }
        selectedMusicViewModel.addOrRemoveSong(song)
    }

    LazyColumn {
        items(
            items = playlistWithSongs.songs,
            key = { it.id }
        ) { song ->
            SongTile(
                song = song,
                onMenuClick = {
                    onMenuClick()
                    getSongInfoViewModel.getSongInfo(song)
                },
                onLongPress = { selectedMusicViewModel.addOrRemoveSong(song) },
                onTap = { onTap(song) },
                selected = selected.contains(song)
            )
        }
    }
}
package com.martini.dartplayer.presentation.components.dashboard.song

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel

@ExperimentalMaterialApi
@Composable
fun SongsLazyColumn(
    songs: List<Song>,
    selected: SelectedMusicViewModel = hiltViewModel(),
    onClick: () -> Unit,
    getSongInfoViewModel: GetSongInfoViewModel = hiltViewModel(),
    playSongsAtIndexViewModel: PlaySongsAtIndexViewModel = hiltViewModel()
) {
    LazyColumn {
        items(
            items = songs,
            key = { it.id }
        ) { song ->
            SongTile(
                song = song,
                onMenuClick = {
                    getSongInfoViewModel.getSongInfo(it)
                    onClick()
                },
                onLongPress = {
                    selected.addOrRemoveSong(it)
                },
                onTap = {
                    if (selected.state.value.music.isNotEmpty()) {
                        selected.addOrRemoveSong(it)
                    } else {
                        playSongsAtIndexViewModel(
                            PlaySongAtIndexParams(
                                songs,
                                songs.indexOf(song)
                            )
                        )
                    }
                },
                selected = selected.state.value.music.contains(song)
            )
        }
    }
}
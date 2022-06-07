package com.martini.dartplayer.presentation.components.albumForArtist.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.SongTile
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistLazyColumn(
    selectedMusic: SelectedMusicViewModel = hiltViewModel(),
    songs: List<Song>,
    getSongInfo: GetSongInfoViewModel = hiltViewModel(),
    onMenuClick: (song: Song) -> Unit,
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
                    getSongInfo.getSongInfo(song)
                    onMenuClick(song)
                },
                onLongPress = { selectedMusic.addOrRemoveSong(song) },
                onTap = {
                    if (selectedMusic.state.value.music.isNotEmpty()) {
                        selectedMusic.addOrRemoveSong(song)
                    } else {
                        playSongsAtIndexViewModel(
                            PlaySongAtIndexParams(
                                songs,
                                songs.indexOf(song)
                            )
                        )
                    }
                },
                selected = selectedMusic.state.value.music.contains(song)
            )
        }
    }
}
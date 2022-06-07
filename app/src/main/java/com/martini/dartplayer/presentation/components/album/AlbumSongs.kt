package com.martini.dartplayer.presentation.components.album

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.GetSongInfoViewModel
import com.martini.dartplayer.presentation.components.dashboard.song.SongTile
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumSongs(
    onMenuClick: (song: Song) -> Unit,
    model: LoadAlbumViewModel = hiltViewModel(),
    selected: SelectedMusicViewModel = hiltViewModel(),
    getSongInfo: GetSongInfoViewModel = hiltViewModel(),
    playSongsAtIndexViewModel: PlaySongsAtIndexViewModel = hiltViewModel()
) {

    when (val state = model.state.value) {
        is LoadAlbumState.Loaded -> {
            val albumData = state.albumWithSongAndArtists
            LazyColumn {
                items(
                    items = albumData.songs.songs,
                    key = { it.id }
                ) { song ->
                    SongTile(
                        song = song,
                        onMenuClick = {
                            getSongInfo.getSongInfo(song)
                            onMenuClick(song)
                        },
                        onLongPress = { selected.addOrRemoveSong(song) },
                        onTap = {
                            if (selected.state.value.music.isNotEmpty()) {
                                selected.addOrRemoveSong(song)
                            } else {
                                playSongsAtIndexViewModel(
                                    PlaySongAtIndexParams(
                                        albumData.songs.songs,
                                        albumData.songs.songs.indexOf(song)
                                    )
                                )
                            }
                        },
                        selected = selected.state.value.music.contains(song)
                    )
                }
            }
        }
        is LoadAlbumState.Failure -> {
            Text(stringResource(R.string.Error))
        }
        else -> {
            CircularProgressIndicator()
        }
    }
}
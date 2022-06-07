package com.martini.dartplayer.presentation.components.playback.bottomSheet

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.playerService.GetCachedPlaylistState
import com.martini.dartplayer.domain.usecases.song.CurrentSongState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.CurrentSongViewModel
import com.martini.dartplayer.presentation.components.playback.viewModels.GetCachedPlaylistViewModel
import com.martini.dartplayer.presentation.components.playback.viewModels.PlayerRemoveSongFromPlaylist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.burnoutcrew.reorderable.*

@ExperimentalMaterialApi
@Composable
fun PlaybackLazyColumn(
    getCachedPlaylistViewModel: GetCachedPlaylistViewModel = hiltViewModel(),
    currentSongViewModel: CurrentSongViewModel = hiltViewModel(),
    playerRemoveSongFromPlaylist: PlayerRemoveSongFromPlaylist = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val stateLibrary = rememberReorderState()

    val currentSong = when (val state = currentSongViewModel.state.value) {
        is CurrentSongState.Loaded -> state.song
        else -> null
    }

    fun reorder(from: ItemPosition, to: ItemPosition) {
        if (from == to) return
        getCachedPlaylistViewModel.reorder(from.index, to.index)
    }

    fun playNext(song: Song, songs: List<Song>) = scope.launch {
        val from = withContext(Dispatchers.Default) {
            songs.indexOf(song)
        }

        val currentIndex = withContext(Dispatchers.Default) {
            songs.indexOf(currentSong)
        }

        if (from < 0 || currentIndex < 0) return@launch
        if (from == currentIndex) return@launch

        if (from < currentIndex) {
            getCachedPlaylistViewModel.reorder(from, currentIndex)
            return@launch
        }

        getCachedPlaylistViewModel.reorder(from, currentIndex + 1)
    }

    fun removeSong(song: Song) {
        playerRemoveSongFromPlaylist(song)
    }

    when (val state = getCachedPlaylistViewModel.state.value) {
        is GetCachedPlaylistState.Loaded -> {
            val songs = state.params.songs

            LaunchedEffect(currentSong) {
                if (currentSong != null) {
                    scope.launch {
                        val index = songs.indexOf(currentSong)
                        if (index != -1) {
                            stateLibrary.listState.scrollToItem(index)
                        }
                    }
                }
            }

            LazyColumn(
                state = stateLibrary.listState,
                modifier = Modifier.reorderable(stateLibrary, { from, to -> reorder(from, to) })
            ) {
                items(
                    items = songs,
                    key = { it.id }
                ) { song ->
                    PlaybackSongTile(
                        modifier = Modifier
                            .draggedItem(stateLibrary.offsetByKey(song.id))
                            .detectReorderAfterLongPress(stateLibrary),
                        song = song,
                        playing = currentSong == song,
                        onClick = { getCachedPlaylistViewModel.jumpTo(songs.indexOf(it)) },
                        playNext = { playNext(song, songs) },
                        removeSong = { removeSong(song) }
                    )
                }
            }
        }
        is GetCachedPlaylistState.Failure -> {
            Text(stringResource(id = R.string.Error))
        }
        else -> CircularProgressIndicator()
    }
}
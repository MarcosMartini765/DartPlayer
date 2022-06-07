package com.martini.dartplayer.presentation.components.dashboard.playlists.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlayNextSongsViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.DeletePlaylistViewModel

@ExperimentalMaterialApi
@Composable
fun PlaylistBottomSheet(
    playlistWithSongs: PlaylistWithSongs,
    onClick: () -> Unit,
    playSongsAtIndexViewModel: PlaySongsAtIndexViewModel = hiltViewModel(),
    playNextSongsViewModel: PlayNextSongsViewModel = hiltViewModel(),
    deletePlaylistViewModel: DeletePlaylistViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var dialogIsOpen by remember {
        mutableStateOf(false)
    }

    fun playSound() {
        playSoundOnTap(context)
    }

    fun play() {
        playSound()
        playSongsAtIndexViewModel(
            PlaySongAtIndexParams(
                songs = playlistWithSongs.songs
            )
        )
        onClick()
    }

    fun playNext() {
        playSound()
        playNextSongsViewModel(
            PlayNextParams(
                selected = Selected(playlistWithSongs.songs)
            )
        )
        onClick()
    }

    fun playNextShuffled() {
        playSound()
        playNextSongsViewModel(
            PlayNextParams(
                selected = Selected(playlistWithSongs.songs),
                shuffled = true
            )
        )
        onClick()
    }

    fun hide() {
        playSound()
        dialogIsOpen = false
    }

    fun show() {
        playSound()
        dialogIsOpen = true
    }

    fun delete() {
        playSound()
        deletePlaylistViewModel(playlistWithSongs)
        onClick()
        hide()
    }

    if (dialogIsOpen) {
        AlertDialog(
            onDismissRequest = { hide() },
            text = { Text(text = "${stringResource(id = R.string.DeletePlaylist)}?") },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { hide() }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { delete() }) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                }
            }
        )
    }

    Column {
        ListItem(
            modifier = Modifier.clickable { play() },
            icon = {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = stringResource(id = R.string.play)
                )
            },
            text = { Text(text = stringResource(id = R.string.play)) }
        )
        ListItem(
            modifier = Modifier.clickable { playNext() },
            icon = {
                Icon(
                    Icons.Filled.SkipNext,
                    contentDescription = stringResource(id = R.string.playNext)
                )
            },
            text = { Text(text = stringResource(id = R.string.playNext)) }
        )
        ListItem(
            modifier = Modifier.clickable { playNextShuffled() },
            icon = {
                Icon(
                    Icons.Filled.Shuffle,
                    contentDescription = stringResource(id = R.string.playNextShuffled)
                )
            },
            text = { Text(text = stringResource(id = R.string.playNextShuffled)) }
        )
        ListItem(
            modifier = Modifier.clickable { show() },
            icon = {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            },
            text = { Text(text = stringResource(id = R.string.delete)) }
        )
    }
}
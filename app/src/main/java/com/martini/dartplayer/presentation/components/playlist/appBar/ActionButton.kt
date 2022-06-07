package com.martini.dartplayer.presentation.components.playlist.appBar

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistState
import com.martini.dartplayer.presentation.components.dashboard.viewModels.ShuffleSongsViewModel
import com.martini.dartplayer.presentation.components.playlist.viewModels.GetPlaylistViewModel

@Composable
fun PlaylistActionButton(
    getPlaylistViewModel: GetPlaylistViewModel = hiltViewModel(),
    shuffleSongsViewModel: ShuffleSongsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val description = stringResource(id = R.string.Shuffle)

    fun shuffle() {
        playSoundOnTap(context)
        when(val state = getPlaylistViewModel.state.value) {
            is GetPlaylistState.Loaded -> {
                shuffleSongsViewModel(state.playlistWithSongs.songs)
            }
            else -> {}
        }
    }

    FloatingActionButton(onClick = { shuffle() }) {
        Icon(Icons.Filled.Shuffle, contentDescription = description)
    }
}
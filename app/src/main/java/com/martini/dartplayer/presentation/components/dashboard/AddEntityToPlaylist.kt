package com.martini.dartplayer.presentation.components.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.params.AddToPlaylistParams
import com.martini.dartplayer.domain.usecases.AddToPlaylistState
import com.martini.dartplayer.domain.usecases.LoadSongsState
import com.martini.dartplayer.presentation.components.AddToPlaylistViewModel
import com.martini.dartplayer.presentation.components.PlaylistSimpleTile
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun AddEntityToPlaylist(
    loadMusicViewModel: LoadMusicViewModel = hiltViewModel(),
    hideDialog: () -> Unit,
    addToPlaylistViewModel: AddToPlaylistViewModel = hiltViewModel(),
    selected: Selected
) {

    val context = LocalContext.current

    fun onClick(playlistWithSongs: PlaylistWithSongs) {
        playSoundOnTap(context)
        addToPlaylistViewModel(
            AddToPlaylistParams(
                selected = selected,
                playlist = playlistWithSongs.playlist
            )
        )
    }

    LaunchedEffect(Unit) {
        snapshotFlow { addToPlaylistViewModel.state.value }
            .distinctUntilChanged()
            .drop(1)
            .onEach {
                if (it is AddToPlaylistState.Loaded) {
                    hideDialog()
                }
            }
            .launchIn(this)
    }

    when (val state = loadMusicViewModel.state.value) {
        is LoadSongsState.Loaded -> {
            val playlists = state.music.playlists

            if (playlists.isEmpty()) {
                Text(stringResource(id = R.string.noPlaylistsFound))
                return
            }

            LazyColumn {
                items(
                    items = playlists,
                    key = { it.playlist.playlistName }
                ) { playlist ->
                    PlaylistSimpleTile(
                        playlist = playlist,
                        onClick = {
                            onClick(it)
                            hideDialog()
                        }
                    )
                }
            }
        }
        is LoadSongsState.Failure -> {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(id = R.string.somethingWentWrong))
            }
        }
        else -> CircularProgressIndicator()
    }
}
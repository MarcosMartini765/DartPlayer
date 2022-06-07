package com.martini.dartplayer.presentation.components.album.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.album.LoadAlbumViewModel
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumPlaySong(
    song: Song,
    onClick: () -> Unit,
    loadAlbumViewModel: LoadAlbumViewModel = hiltViewModel(),
    playSongsAtIndexViewModel: PlaySongsAtIndexViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    fun play() {
        playSoundOnTap(context)
        when (val state = loadAlbumViewModel.state.value) {
            is LoadAlbumState.Loaded -> {
                val songs = state.albumWithSongAndArtists.songs.songs
                playSongsAtIndexViewModel(
                    PlaySongAtIndexParams(
                        songs,
                        songs.indexOf(song)
                    )
                )
            }
            else -> {}
        }
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { play() },
        icon = { Icon(Icons.Filled.PlayArrow, contentDescription = stringResource(R.string.play)) },
        text = { Text(stringResource(R.string.play)) }
    )
}
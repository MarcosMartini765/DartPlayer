package com.martini.dartplayer.presentation.components.dashboard.bottomSheet

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
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlaySongsAtIndexViewModel

@ExperimentalMaterialApi
@Composable
fun PlaySongComponent(
    songs: List<Song>,
    playSongsAtIndexViewModel: PlaySongsAtIndexViewModel = hiltViewModel(),
    song: Song,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    fun playSong() {
        playSoundOnTap(context)
        playSongsAtIndexViewModel(
            PlaySongAtIndexParams(
                songs,
                songs.indexOf(song)
            )
        )
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { playSong() },
        icon = { Icon(Icons.Filled.PlayArrow, contentDescription = stringResource(R.string.play)) },
        text = { Text(stringResource(R.string.play)) }
    )
}
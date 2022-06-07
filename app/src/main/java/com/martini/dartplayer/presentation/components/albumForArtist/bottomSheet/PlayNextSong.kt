package com.martini.dartplayer.presentation.components.albumForArtist.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.common.playSoundOnTap
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlayNextSongViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumForArtistPlayNextSong(
    playNextSongViewModel: PlayNextSongViewModel = hiltViewModel(),
    song: Song,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    fun play() {
        playSoundOnTap(context)
        playNextSongViewModel(song)
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { play() },
        icon = {
            Icon(
                Icons.Filled.SkipNext,
                contentDescription = stringResource(R.string.playNext)
            )
        },
        text = { Text(stringResource(R.string.playNext)) }
    )
}
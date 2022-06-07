package com.martini.dartplayer.presentation.components.dashboard.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlayNextSongsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayNextAlbumsShuffled(
    playNextSongsViewModel: PlayNextSongsViewModel = hiltViewModel(),
    albumWithSongs: AlbumWithSongs,
    onClick: () -> Unit
) {

    fun play() {
        playNextSongsViewModel(
            PlayNextParams(
                selected = Selected(listOf(albumWithSongs)),
                shuffled = true
            )
        )
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { play() },
        icon = {
            Icon(
                Icons.Filled.Shuffle,
                contentDescription = stringResource(R.string.playNextShuffled)
            )
        },
        text = { Text(stringResource(R.string.playNextShuffled)) }
    )
}
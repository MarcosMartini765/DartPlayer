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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PlayAlbumViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayAlbum(
    albumWithSongs: AlbumWithSongs,
    playAlbumViewModel: PlayAlbumViewModel = hiltViewModel(),
    onClick: () -> Unit
) {

    fun play() {
        playAlbumViewModel(albumWithSongs)
        onClick()
    }

    ListItem(
        modifier = Modifier.clickable { play() },
        icon = { Icon(Icons.Filled.PlayArrow, contentDescription = stringResource(R.string.play)) },
        text = { Text(stringResource(R.string.play)) }
    )
}
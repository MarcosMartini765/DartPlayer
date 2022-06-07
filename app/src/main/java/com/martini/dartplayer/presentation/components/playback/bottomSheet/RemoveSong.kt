package com.martini.dartplayer.presentation.components.playback.bottomSheet

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@ExperimentalMaterialApi
@Composable
fun PlaybackRemoveSong(
    onClick: () -> Unit
) {
    DropdownMenuItem(onClick = { onClick() }) {
        ListItem(
            text = { Text(text = stringResource(id = R.string.RemoveFromPlaylist)) },
            icon = {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = stringResource(id = R.string.RemoveFromPlaylist)
                )
            }
        )
    }
}
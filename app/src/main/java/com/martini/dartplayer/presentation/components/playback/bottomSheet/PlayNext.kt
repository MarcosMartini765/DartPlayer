package com.martini.dartplayer.presentation.components.playback.bottomSheet

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@ExperimentalMaterialApi
@Composable
fun PlaybackPlayNext(
    onClick: () -> Unit
) {

    DropdownMenuItem(onClick = { onClick() }) {
        ListItem(
            text = { Text(text = stringResource(id = R.string.playNext)) },
            icon = {
                Icon(
                    Icons.Filled.SkipNext,
                    contentDescription = stringResource(id = R.string.playNext)
                )
            }
        )
    }
}
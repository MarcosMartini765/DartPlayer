package com.martini.dartplayer.presentation.components.playback.bottomSheet

import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@ExperimentalMaterialApi
@Composable
fun PlaybackSongDropDown(
    playNext: () -> Unit,
    removeSong: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    fun hide() {
        expanded = false
    }

    fun show() {
        expanded = true
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { hide() }) {
        PlaybackPlayNext(
            onClick = {
                playNext()
                hide()
            }
        )
        PlaybackRemoveSong(
            onClick = {
                removeSong()
                hide()
            }
        )
    }

    val description = stringResource(id = R.string.settings)

    IconButton(onClick = { show() }) {
        Icon(Icons.Filled.MoreVert, contentDescription = description)
    }
}
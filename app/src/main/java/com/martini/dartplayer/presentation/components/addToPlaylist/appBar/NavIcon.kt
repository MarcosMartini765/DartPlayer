package com.martini.dartplayer.presentation.components.addToPlaylist.appBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@Composable
fun AddToPlaylistAppBarNavIcon(
    popBack: () -> Unit
) {
    val description = stringResource(id = R.string.back)

    IconButton(onClick = { popBack() }) {
        Icon(Icons.Filled.Clear, contentDescription = description)
    }
}
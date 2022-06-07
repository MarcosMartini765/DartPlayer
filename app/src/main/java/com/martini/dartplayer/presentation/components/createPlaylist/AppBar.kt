package com.martini.dartplayer.presentation.components.createPlaylist

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@Composable
fun CreatePlaylistAppBar(
    popBack: () -> Unit,
) {

    var text by remember { mutableStateOf("") }

    fun onChange(newText: String) {
        text = newText
    }

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { popBack() }) {
                Icon(Icons.Filled.Clear, contentDescription = stringResource(id = R.string.back))
            }
        },
        title = {
            CreatePlaylistTextField(
                onChange = { onChange(it) },
                text = text
            )
        },
        actions = {
            CreatePlaylistButton(
                text = text,
            )
        }
    )
}
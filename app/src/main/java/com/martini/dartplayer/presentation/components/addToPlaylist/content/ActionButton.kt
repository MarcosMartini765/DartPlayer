package com.martini.dartplayer.presentation.components.addToPlaylist.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.CreatePlaylistViewModel

@Composable
fun AddToPlaylistActionButton(
    createPlaylistViewModel: CreatePlaylistViewModel = hiltViewModel(),
    description: String = stringResource(id = R.string.CreatePlaylist),
    floating: Boolean = true
) {
    var dialogIsOpen by remember {
        mutableStateOf(false)
    }

    var text by remember {
        mutableStateOf("")
    }

    fun show() {
        dialogIsOpen = true
    }

    fun hide() {
        dialogIsOpen = false
    }

    fun create() {
        if (text.isEmpty() || text.length <= 2) return
        hide()
        createPlaylistViewModel(text)
        text = ""
    }

    if (dialogIsOpen) {
        AlertDialog(
            onDismissRequest = { hide() },
            title = { Text(text = description) },
            text = {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { hide() }) {
                        Text(text = stringResource(id = R.string.back))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(onClick = { create() }) {
                        Text(text = stringResource(id = R.string.Create))
                    }
                }
            }
        )
    }

    if (!floating) {
        return Button(
            onClick = { show() },
        ) {
            Text(
                text = description,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    FloatingActionButton(
        onClick = { show() },
    ) {
        Text(
            text = description,
            modifier = Modifier.padding(16.dp)
        )
    }
}
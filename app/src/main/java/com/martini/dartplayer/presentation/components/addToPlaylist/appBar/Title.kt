package com.martini.dartplayer.presentation.components.addToPlaylist.appBar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R

@Composable
fun AddToPlaylistAppBarTitle() {
    val description = stringResource(id = R.string.AddToPlaylist)

    Text(text = description)
}
package com.martini.dartplayer.presentation.components.artist.appbar.dropDown

import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.martini.dartplayer.R
import com.martini.dartplayer.presentation.components.AddSelectedToPlaylist

@ExperimentalMaterialApi
@Composable
fun ArtistAppBarDropDown() {

    var showMenu by remember { mutableStateOf(false) }

    fun hideDrop() {
        showMenu = false
    }

    fun showDrop() {
        showMenu = true
    }

    DropdownMenu(expanded = showMenu, onDismissRequest = { hideDrop() }) {
        ArtistAppBarDeleteArtist(onClick = { hideDrop() })
        AddSelectedToPlaylist()
    }

    IconButton(onClick = { showDrop() }) {
        Icon(Icons.Filled.MoreVert, contentDescription = stringResource(R.string.more))
    }

}
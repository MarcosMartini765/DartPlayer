package com.martini.dartplayer.presentation.components.albumForArtist.appbar.dropdown

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
fun AlbumForArtistAppBarDropDown(

) {
    var menuOpened by remember { mutableStateOf(false) }

    fun hideMenu() {
        menuOpened = false
    }

    fun showMenu() {
        menuOpened = true
    }

    DropdownMenu(expanded = menuOpened, onDismissRequest = { hideMenu() }) {
        AlbumForArtistAppBarDeleteAlbum(
            onClick = { hideMenu() },
        )
        AddSelectedToPlaylist()
    }

    IconButton(onClick = { showMenu() }) {
        Icon(Icons.Filled.MoreVert, contentDescription = stringResource(R.string.more))
    }

}
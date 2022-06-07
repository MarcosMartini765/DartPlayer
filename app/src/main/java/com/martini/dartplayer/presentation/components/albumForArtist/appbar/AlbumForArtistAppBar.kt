package com.martini.dartplayer.presentation.components.albumForArtist.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.martini.dartplayer.presentation.components.albumForArtist.appbar.dropdown.AlbumForArtistAppBarDropDown

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistAppBar(
    popBack: () -> Unit
) {
    TopAppBar(
        title = { AlbumForArtistAppBarTitle() },
        navigationIcon = {
            AlbumForArtistAppBarNavIcon(
                popBack = popBack
            )
        },
        actions = {
            AlbumForArtistAppBarDelete(popBack = popBack)
            AlbumForArtistAppBarDropDown()
        }
    )
}
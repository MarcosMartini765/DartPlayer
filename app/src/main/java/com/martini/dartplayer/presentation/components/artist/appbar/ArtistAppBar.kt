package com.martini.dartplayer.presentation.components.artist.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.components.artist.appbar.dropDown.ArtistAppBarDropDown

@ExperimentalMaterialApi
@Composable
fun ArtistAppBar(
    navController: NavController
) {
    TopAppBar(
        title = {
            ArtistAppBarTitle()
        },
        navigationIcon = {
            ArtistAppBarNavIcon(navController)
        },
        actions = {
            ArtistAppBarDelete(
                popBack = { navController.popBackStack() }
            )
            ArtistAppBarDropDown()
        }
    )
}
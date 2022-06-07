package com.martini.dartplayer.presentation.components.album.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martini.dartplayer.domain.usecases.album.DeleteAlbumForAlbumState
import com.martini.dartplayer.domain.usecases.album.LoadAlbumState
import com.martini.dartplayer.presentation.components.album.LoadAlbumViewModel
import com.martini.dartplayer.presentation.components.album.appbar.dropDown.AlbumAppBarDropDown
import com.martini.dartplayer.presentation.components.album.models.DeleteAlbumForAlbumViewModel

@ExperimentalMaterialApi
@Composable
fun AlbumAppBar(
    navController: NavController,
    deleteAlbum: DeleteAlbumForAlbumViewModel = hiltViewModel(),
    loadAlbum: LoadAlbumViewModel = hiltViewModel()
) {

    fun popBack() {
        navController.popBackStack()
    }

    when (val state = loadAlbum.state.value) {
        is LoadAlbumState.Loaded -> {
            if (state.albumWithSongAndArtists.songs.songs.isEmpty()) {
                LaunchedEffect(Unit) {
                    deleteAlbum(state.albumWithSongAndArtists.songs)
                }
            }
        }
        else -> {}
    }

    if (deleteAlbum.state.value is DeleteAlbumForAlbumState.Loaded) {
        LaunchedEffect(Unit) {
            popBack()
        }
    }

    TopAppBar(
        title = {
            AlbumAppBarTitle()
        },
        navigationIcon = {
            AlbumAppBarNavIcon(navController)
        },
        actions = {
            AlbumAppBarDelete()
            AlbumAppBarDropDown()
        }
    )
}
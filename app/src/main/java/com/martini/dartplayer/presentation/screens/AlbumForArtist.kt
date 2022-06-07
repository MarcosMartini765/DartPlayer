package com.martini.dartplayer.presentation.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.albumForArtist.appbar.AlbumForArtistActionButton
import com.martini.dartplayer.presentation.components.albumForArtist.appbar.AlbumForArtistAppBar
import com.martini.dartplayer.presentation.components.albumForArtist.bottomSheet.AlbumForArtistBottomSheetContent
import com.martini.dartplayer.presentation.components.albumForArtist.content.AlbumForArtistContent
import com.martini.dartplayer.presentation.components.dashboard.bottomAppBar.DashboardBottomAppBar
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AlbumForArtistScreen(navController: NavController) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    fun closeBottomSheet() {
        scope.launch {
            sheetState.hide()
        }
    }

    fun popBack() {
        navController.popBackStack()
    }

    fun handleMenu() {
        scope.launch {
            sheetState.apply {
                if (isVisible) hide()
                else show()
            }
        }
    }

    fun navigateToPlayback() {
        navController.navigate(Screen.Playback.route)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            AlbumForArtistBottomSheetContent(
                onClick = { closeBottomSheet() }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    AlbumForArtistAppBar(
                        popBack = { popBack() }
                    )
                },
                content = {
                    AlbumForArtistContent(onMenuClick = {
                        handleMenu()
                    })
                },
                bottomBar = {
                    DashboardBottomAppBar(
                        navigateToPlaybackScreen = { navigateToPlayback() }
                    )
                },
                floatingActionButton = { AlbumForArtistActionButton() }
            )
        },
    )
}
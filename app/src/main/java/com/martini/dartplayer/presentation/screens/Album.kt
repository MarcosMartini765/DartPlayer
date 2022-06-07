package com.martini.dartplayer.presentation.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.album.AlbumContent
import com.martini.dartplayer.presentation.components.album.appbar.AlbumActionButton
import com.martini.dartplayer.presentation.components.album.appbar.AlbumAppBar
import com.martini.dartplayer.presentation.components.album.bottomSheet.AlbumBottomSheetContent
import com.martini.dartplayer.presentation.components.dashboard.bottomAppBar.DashboardBottomAppBar
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AlbumScreen(
    navController: NavController,
) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    fun hideBottomSheet() {
        scope.launch {
            sheetState.apply {
                hide()
            }
        }
    }

    fun navigateToPlayback() {
        navController.navigate(Screen.Playback.route)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            AlbumBottomSheetContent(
                onClick = { hideBottomSheet() }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AlbumAppBar(
                    navController = navController
                )
            },
            content = {
                AlbumContent(onMenuClick = {
                    scope.launch {
                        sheetState.apply {
                            if (isVisible) hide()
                            else show()
                        }
                    }
                })
            },
            bottomBar = {
                DashboardBottomAppBar(
                    navigateToPlaybackScreen = { navigateToPlayback() }
                )
            },
            floatingActionButton = { AlbumActionButton() }
        )
    }
}
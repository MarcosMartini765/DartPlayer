package com.martini.dartplayer.presentation.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.dashboard.bottomAppBar.DashboardBottomAppBar
import com.martini.dartplayer.presentation.components.playlist.appBar.PlaylistActionButton
import com.martini.dartplayer.presentation.components.playlist.appBar.PlaylistAppBar
import com.martini.dartplayer.presentation.components.playlist.bottomSheet.PlaylistBottomSheetContent
import com.martini.dartplayer.presentation.components.playlist.content.PlaylistContent
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PlaylistScreen(navController: NavController) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    fun popBack() {
        navController.popBackStack()
    }

    fun hideBottomSheet() {
        scope.launch {
            sheetState.apply {
                hide()
            }
        }
    }

    fun showBottomSheet() {
        scope.launch {
            sheetState.apply {
                show()
            }
        }
    }

    fun navigateToPlayback() {
        navController.navigate(Screen.Playback.route)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            PlaylistBottomSheetContent(
                onClick = { hideBottomSheet() }
            )
        },
        content = {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { PlaylistAppBar(popBack = { popBack() }) },
                content = {
                    PlaylistContent(
                        onMenuClick = { showBottomSheet() }
                    )
                },
                bottomBar = {
                    DashboardBottomAppBar(
                        navigateToPlaybackScreen = { navigateToPlayback() }
                    )
                },
                floatingActionButton = { PlaylistActionButton() }
            )
        },
    )
}
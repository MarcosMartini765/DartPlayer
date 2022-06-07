package com.martini.dartplayer.presentation.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.artist.ArtistActionButton
import com.martini.dartplayer.presentation.components.artist.ArtistContent
import com.martini.dartplayer.presentation.components.artist.appbar.ArtistAppBar
import com.martini.dartplayer.presentation.components.artist.bottomSheet.ArtistBottomSheetContent
import com.martini.dartplayer.presentation.components.dashboard.bottomAppBar.DashboardBottomAppBar
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ArtistScreen(navController: NavController) {

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
            ArtistBottomSheetContent(
                onClick = { hideBottomSheet() }
            )
        }
    ) {
        Scaffold(
            topBar = {
                ArtistAppBar(
                    navController = navController
                )
            },
            content = {
                ArtistContent(
                    onMenuClick = {
                        scope.launch {
                            sheetState.apply {
                                if (isVisible) hide()
                                else show()
                            }
                        }
                    },
                )
            },
            bottomBar = {
                DashboardBottomAppBar(
                    navigateToPlaybackScreen = { navigateToPlayback() }
                )
            },
            floatingActionButton = { ArtistActionButton() }
        )
    }
}
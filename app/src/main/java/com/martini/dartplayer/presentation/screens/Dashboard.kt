package com.martini.dartplayer.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.dashboard.BottomSheetContent
import com.martini.dartplayer.presentation.components.dashboard.DashboardActionButton
import com.martini.dartplayer.presentation.components.dashboard.album.DashboardAlbumsContent
import com.martini.dartplayer.presentation.components.dashboard.appbar.DashboardAppBar
import com.martini.dartplayer.presentation.components.dashboard.artist.DashboardArtistsContent
import com.martini.dartplayer.presentation.components.dashboard.bottomAppBar.DashboardBottomAppBar
import com.martini.dartplayer.presentation.components.dashboard.playlists.content.DashboardPlaylist
import com.martini.dartplayer.presentation.components.dashboard.song.DashboardSongsContent
import com.martini.dartplayer.presentation.components.dashboard.viewModels.PageStateViewModel
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun DashboardScreen(
    navController: NavController,
    pageStateViewModel: PageStateViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(Constants.TABS_SONGS)
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val systemUiController = rememberSystemUiController()

    val systemColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            systemColor
        )
    }

    fun toggleBottomSheet() {
        scope.launch {
            sheetState.apply {
                if (isVisible) hide()
                else show()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent(
                goToArtist = {
                    scope.launch {
                        sheetState.apply {
                            hide()
                        }
                        navController.navigate(Screen.Artist.passName(it))
                    }
                },
                onClick = {
                    scope.launch {
                        sheetState.apply {
                            hide()
                        }
                    }
                },
            )
        },
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                DashboardAppBar(
                    navController = navController,
                    tabIndex = pagerState.currentPage,
                    onChange = {
                        scope.launch {
                            pageStateViewModel(it)
                            pagerState.animateScrollToPage(it)
                        }
                    }
                )
            },
            content = { padding ->
                Box(
                    Modifier.padding(padding),
                ) {
                    HorizontalPager(count = 4, state = pagerState) { page ->
                        when (page) {
                            Constants.TABS_SONGS -> DashboardSongsContent(
                                onClick = {
                                    toggleBottomSheet()
                                },
                            )
                            Constants.TABS_ALBUMS -> DashboardAlbumsContent(
                                onClick = {
                                    toggleBottomSheet()
                                }
                            )
                            Constants.TABS_ARTISTS -> DashboardArtistsContent(
                                onArtistClick = {
                                    toggleBottomSheet()
                                },
                            )
                            Constants.TABS_PLAYLISTS -> DashboardPlaylist(
                                navigateToPlaylist = {
                                    navController.navigate(Screen.Playlist.passName(it.playlist.playlistName))
                                },
                                onMenuClick = { toggleBottomSheet() }
                            )
                        }
                    }
                }
            },
            bottomBar = {
                DashboardBottomAppBar(
                    navigateToPlaybackScreen = {
                        navController.navigate(Screen.Playback.route)
                    }
                )
            },
            floatingActionButton = {
                DashboardActionButton()
            }
        )
    }
}
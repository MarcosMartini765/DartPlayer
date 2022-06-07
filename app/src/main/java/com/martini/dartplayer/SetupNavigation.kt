package com.martini.dartplayer

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.domain.usecases.NavigationAction
import com.martini.dartplayer.domain.usecases.PlayRequestedSongState
import com.martini.dartplayer.presentation.Screen
import com.martini.dartplayer.presentation.components.NavigationViewModel
import com.martini.dartplayer.presentation.components.viewModels.PlayRequestedSongViewModel
import com.martini.dartplayer.presentation.screens.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    playRequestedSongViewModel: PlayRequestedSongViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

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

    fun navigateToAddPlaylist() {
        navController.navigate(Screen.AddToPlaylist.route)
    }

    fun navigateToArtist(name: String) {
        navController.navigate(Screen.Artist.passName(name))
    }

    fun navigateToAlbumForArtist(
        album: String,
        artist: String
    ) {
        navController.navigate(Screen.AlbumForArtist.passArgs(album, artist))
    }

    fun navigateToAlbum(name: String) {
        navController.navigate(Screen.Album.passName(name))
    }

    fun navigateToSettings() {
        navController.navigate(Screen.Settings.route)
    }

    LaunchedEffect(Unit) {
        playRequestedSongViewModel.listen()
            .onEach {
                if (it is PlayRequestedSongState.Loaded) {
                    playRequestedSongViewModel.playSong(it.song)
                }
            }
            .launchIn(scope)
        navigationViewModel.listen()
            .onEach {
                when (it) {
                    is NavigationAction.NavigateToAddPlaylist -> navigateToAddPlaylist()
                    is NavigationAction.NavigateToArtist -> navigateToArtist(it.name)
                    is NavigationAction.NavigateToAlbumForArtist -> navigateToAlbumForArtist(
                        it.album,
                        it.artist
                    )
                    is NavigationAction.NavigateToAlbum -> navigateToAlbum(it.name)
                    is NavigationAction.NavigateToSettings -> navigateToSettings()
                    else -> {}
                }
            }
            .launchIn(scope)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(
            route = Screen.Dashboard.route
        ) {
            DashboardScreen(navController)
        }
        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(navController)
        }

        composable(
            route = Screen.Album.route,
            arguments = listOf(navArgument(Constants.ALBUM_SCREEN_ARGUMENT_NAME) {
                type = NavType.StringType
            })
        ) {
            AlbumScreen(navController)
        }
        composable(
            route = Screen.Artist.route,
            arguments = listOf(navArgument(Constants.ARTIST_SCREEN_ARGUMENT_NAME) {
                type = NavType.StringType
            })
        ) {
            ArtistScreen(navController)
        }
        composable(
            route = Screen.ToDelete.route
        ) {
            ToDeleteScreen(navController)
        }
        composable(
            route = Screen.AlbumForArtist.route,
            arguments = listOf(navArgument(Constants.ALBUM_FOR_ARTIST_ALBUM_ARG) {
                type = NavType.StringType
            }, navArgument(Constants.ALBUM_FOR_ARTIST_ARTIST_ARG) {
                type = NavType.StringType
            })
        ) {
            AlbumForArtistScreen(navController)
        }
        composable(
            route = Screen.Playback.route
        ) {
            PlaybackScreen(navController)
        }
        composable(
            route = Screen.Playlist.route,
            arguments = listOf(navArgument(
                Constants.PLAYLIST_SCREEN_ARG_NAME
            ) {
                type = NavType.StringType
            })
        ) {
            PlaylistScreen(navController = navController)
        }
        composable(
            route = Screen.AddToPlaylist.route
        ) {
            AddToPlaylistScreen(navController = navController)
        }
    }
}
package com.martini.dartplayer.presentation

import com.martini.dartplayer.common.Constants

sealed class Screen(val route: String) {
    object Dashboard : Screen("Dashboard")
    object Settings : Screen("Settings")
    object Album : Screen("Album/{${Constants.ALBUM_SCREEN_ARGUMENT_NAME}}") {
        fun passName(name: String): String {
            return "Album/$name"
        }
    }

    object Artist : Screen("Artist/{${Constants.ARTIST_SCREEN_ARGUMENT_NAME}}") {
        fun passName(name: String): String {
            return "Artist/$name"
        }
    }

    object ToDelete : Screen("toDelete")
    object AlbumForArtist :
        Screen("AlbumForArtist/{${Constants.ALBUM_FOR_ARTIST_ALBUM_ARG}}/{${Constants.ALBUM_FOR_ARTIST_ARTIST_ARG}}") {
        fun passArgs(album: String, artist: String): String {
            return "AlbumForArtist/$album/$artist"
        }
    }

    object Playback : Screen("Playback")
    object Playlist : Screen("Playlist/{${Constants.PLAYLIST_SCREEN_ARG_NAME}}") {
        fun passName(name: String): String {
            return "Playlist/$name"
        }
    }
    object AddToPlaylist: Screen("AddToPlaylist")
}

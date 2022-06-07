package com.martini.dartplayer.domain.usecases

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class NavigationUseCase {
    val listen = MutableSharedFlow<NavigationAction>(0)

    fun navigateToAddPlaylist() = flow {
        emit(NavigationAction.NavigateToAddPlaylist)
        listen.emit(NavigationAction.NavigateToAddPlaylist)
    }

    fun navigateToArtist(name: String) = flow {
        emit(NavigationAction.NavigateToArtist(name))
        listen.emit(NavigationAction.NavigateToArtist(name))
    }

    fun navigateToAlbumForArtist(
        album: String,
        artist: String
    ) = flow {
        emit(NavigationAction.NavigateToAlbumForArtist(album, artist))
        listen.emit(NavigationAction.NavigateToAlbumForArtist(album, artist))
    }

    fun navigateToAlbum(name: String) = flow {
        emit(NavigationAction.NavigateToAlbum(name))
        listen.emit(NavigationAction.NavigateToAlbum(name))
    }

    fun navigateToSettings() = flow {
        emit(NavigationAction.NavigateToSettings)
        listen.emit(NavigationAction.NavigateToSettings)
    }
}

sealed class NavigationAction {
    object Initial : NavigationAction()
    object NavigateToAddPlaylist : NavigationAction()

    class NavigateToArtist(
        val name: String
    ) : NavigationAction()

    class NavigateToAlbumForArtist(
        val album: String,
        val artist: String
    ) : NavigationAction()

    class NavigateToAlbum(
        val name: String
    ) : NavigationAction()

    object NavigateToSettings : NavigationAction()
}
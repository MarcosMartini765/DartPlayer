package com.martini.dartplayer.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.usecases.NavigationAction
import com.martini.dartplayer.domain.usecases.NavigationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigationUseCase: NavigationUseCase
) : ViewModel() {


    fun listen(): MutableSharedFlow<NavigationAction> {
        return navigationUseCase.listen
    }

    fun navigateToAddPlaylist() {
        navigationUseCase.navigateToAddPlaylist()
            .launchIn(viewModelScope)
    }

    fun navigateToArtist(name: String) {
        navigationUseCase.navigateToArtist(name)
            .launchIn(viewModelScope)
    }

    fun navigateToAlbumForArtist(
        album: String,
        artist: String
    ) {
        navigationUseCase.navigateToAlbumForArtist(album, artist)
            .launchIn(viewModelScope)
    }

    fun navigateToAlbum(name: String) {
        navigationUseCase.navigateToAlbum(name)
            .launchIn(viewModelScope)
    }

    fun navigateToSettings() {
        navigationUseCase.navigateToSettings()
            .launchIn(viewModelScope)
    }
}
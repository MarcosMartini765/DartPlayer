package com.martini.dartplayer.domain.usecases

import com.martini.dartplayer.domain.params.AddToPlaylistParams
import com.martini.dartplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(
        params: AddToPlaylistParams
    ) = flow {
        try {
            emit(AddToPlaylistState.Loading)
            playlistRepository.addToPlaylist(params)
            emit(AddToPlaylistState.Loaded)
        } catch (e: Exception) {
            emit(AddToPlaylistState.Failure)
        }
    }
}

sealed class AddToPlaylistState {
    object Initial : AddToPlaylistState()
    object Loading : AddToPlaylistState()
    object Loaded : AddToPlaylistState()
    object Failure : AddToPlaylistState()
}
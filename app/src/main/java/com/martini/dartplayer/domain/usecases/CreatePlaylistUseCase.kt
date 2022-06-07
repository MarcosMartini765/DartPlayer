package com.martini.dartplayer.domain.usecases

import com.martini.dartplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreatePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository,
) {
    operator fun invoke(name: String) = flow {
        try {
            emit(CreatePlaylistState.Loading)
            playlistRepository.createPlaylist(name)
            emit(CreatePlaylistState.Loaded)
        } catch (e: Exception) {
            emit(CreatePlaylistState.Failure)
        }
    }
}

sealed class CreatePlaylistState {
    object Initial : CreatePlaylistState()
    object Loading : CreatePlaylistState()
    object Loaded : CreatePlaylistState()
    object Failure : CreatePlaylistState()
}
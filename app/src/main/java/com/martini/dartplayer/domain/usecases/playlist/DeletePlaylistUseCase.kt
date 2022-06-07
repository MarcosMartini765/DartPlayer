package com.martini.dartplayer.domain.usecases.playlist

import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    val listen = MutableSharedFlow<DeletePlaylistState>(0)

    operator fun invoke(playlistWithSongs: PlaylistWithSongs) = flow {
        try {
            emit(DeletePlaylistState.Loading)
            listen.emit(DeletePlaylistState.Loading)
            playlistRepository.deletePlaylist(playlistWithSongs)
            emit(DeletePlaylistState.Loaded)
            listen.emit(DeletePlaylistState.Loaded)
        } catch (e: Exception) {
            emit(DeletePlaylistState.Failure)
            listen.emit(DeletePlaylistState.Failure)
        }
    }
}

sealed class DeletePlaylistState {
    object Loading: DeletePlaylistState()
    object Loaded: DeletePlaylistState()
    object Failure: DeletePlaylistState()
}
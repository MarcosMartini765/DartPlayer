package com.martini.dartplayer.domain.usecases.playlist

import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveSongFromPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    val listen = MutableSharedFlow<RemoveSongFromPlaylistState>(0)

    operator fun invoke(selected: Selected, playlistWithSongs: PlaylistWithSongs) = flow {
        try {
            emit(RemoveSongFromPlaylistState.Loading)
            listen.emit(RemoveSongFromPlaylistState.Loading)
            playlistRepository.removeSongFromPlaylist(selected, playlistWithSongs)
            emit(RemoveSongFromPlaylistState.Loaded)
            listen.emit(RemoveSongFromPlaylistState.Loaded)
        } catch (e: Exception) {
            emit(RemoveSongFromPlaylistState.Failure)
            listen.emit(RemoveSongFromPlaylistState.Failure)
        }
    }
}

sealed class RemoveSongFromPlaylistState {
    object Loading : RemoveSongFromPlaylistState()
    object Loaded : RemoveSongFromPlaylistState()
    object Failure : RemoveSongFromPlaylistState()
}
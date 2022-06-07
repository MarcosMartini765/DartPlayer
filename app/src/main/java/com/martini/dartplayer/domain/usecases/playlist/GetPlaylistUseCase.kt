package com.martini.dartplayer.domain.usecases.playlist

import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    val listen = MutableStateFlow<GetPlaylistState>(GetPlaylistState.Initial)

    operator fun invoke(name: String) = flow {
        try {
            emit(GetPlaylistState.Loading)
            listen.emit(GetPlaylistState.Loading)
            val playlist = playlistRepository.getPlaylist(name)
            emit(GetPlaylistState.Loaded(playlist))
            listen.emit(GetPlaylistState.Loaded(playlist))
        } catch (e: Exception) {
            emit(GetPlaylistState.Failure)
            listen.emit(GetPlaylistState.Failure)
        }
    }
}

sealed class GetPlaylistState {
    object Initial : GetPlaylistState()
    object Loading : GetPlaylistState()
    class Loaded(
        val playlistWithSongs: PlaylistWithSongs
    ) : GetPlaylistState()

    object Failure : GetPlaylistState()
}
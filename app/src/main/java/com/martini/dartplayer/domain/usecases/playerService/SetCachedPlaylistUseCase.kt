package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.params.player.SetCachedPlaylistParams
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetCachedPlaylistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableSharedFlow<SetCachedPlaylistState>(0)

    operator fun invoke(params: SetCachedPlaylistParams) = flow {
        try {
            emit(SetCachedPlaylistState.Loading)
            listen.emit(SetCachedPlaylistState.Loading)
            musicRepository.setCachedPlaylist(params)
            emit(SetCachedPlaylistState.Loaded)
            listen.emit(SetCachedPlaylistState.Loaded)
        } catch (e: Exception) {
            emit(SetCachedPlaylistState.Failure)
            listen.emit(SetCachedPlaylistState.Failure)
        }
    }
}

sealed class SetCachedPlaylistState {
    object Loading : SetCachedPlaylistState()
    object Loaded : SetCachedPlaylistState()
    object Failure : SetCachedPlaylistState()
}
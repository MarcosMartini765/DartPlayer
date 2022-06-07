package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.params.player.GetCachedPlaylistParams
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCachedPlaylistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke() = flow {
        try {
            val params = musicRepository.getCachedPlaylist()
            emit(GetCachedPlaylistState.Loaded(params))
        } catch (e: Exception) {
            emit(GetCachedPlaylistState.Failure)
        }
    }
}

sealed class GetCachedPlaylistState {
    object Initial : GetCachedPlaylistState()
    class Loaded(
        val params: GetCachedPlaylistParams
    ) : GetCachedPlaylistState()

    object Failure : GetCachedPlaylistState()
}
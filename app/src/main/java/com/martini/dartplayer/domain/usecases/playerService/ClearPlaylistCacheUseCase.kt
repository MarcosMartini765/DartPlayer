package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearPlaylistCacheUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke() = flow {
        try {
            emit(ClearPlaylistCacheState.Loading)
            musicRepository.clearCachedPlaylist()
            emit(ClearPlaylistCacheState.Loaded)
        } catch (e: Exception) {
            emit(ClearPlaylistCacheState.Failure)
        }
    }
}

sealed class ClearPlaylistCacheState {
    object Loading : ClearPlaylistCacheState()
    object Loaded : ClearPlaylistCacheState()
    object Failure : ClearPlaylistCacheState()
}
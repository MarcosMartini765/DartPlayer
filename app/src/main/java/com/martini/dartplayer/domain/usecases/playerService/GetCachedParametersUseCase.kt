package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCachedParametersUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke() = flow {
        try {
            emit(GetCachedParamsState.Loading)
            val params = musicRepository.getCachedPlaylistParams()
            emit(GetCachedParamsState.Loaded(params))
        } catch (e: Exception) {
            emit(GetCachedParamsState.Failure)
        }
    }
}

sealed class GetCachedParamsState {
    object Loading : GetCachedParamsState()
    class Loaded(
        val params: CachedPlaybackParameters
    ) : GetCachedParamsState()

    object Failure : GetCachedParamsState()
}
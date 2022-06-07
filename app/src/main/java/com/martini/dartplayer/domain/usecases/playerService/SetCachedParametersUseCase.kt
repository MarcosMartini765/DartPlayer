package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetCachedParametersUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(params: CachedPlaybackParameters) = flow {
        try {
            emit(SetCachedParamsState.Loading)
            musicRepository.updateCachedPlaylistParams(params)
            emit(SetCachedParamsState.Loaded)
        } catch (e: Exception) {
            emit(SetCachedParamsState.Failure)
        }
    }
}

sealed class SetCachedParamsState {
    object Loading : SetCachedParamsState()
    object Loaded : SetCachedParamsState()

    object Failure : SetCachedParamsState()
}
package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayNextSongsUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(
        params: PlayNextParams
    ) = flow {
        try {
            emit(PlayNextSongsState.Loading)
            playerRepository.playNext(params)
            emit(PlayNextSongsState.Loaded)
        } catch (e: Exception) {
            emit(PlayNextSongsState.Failure)
        }
    }
}

sealed class PlayNextSongsState {
    object Loading : PlayNextSongsState()
    object Loaded : PlayNextSongsState()
    object Failure : PlayNextSongsState()
}
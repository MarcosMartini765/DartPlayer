package com.martini.dartplayer.domain.usecases.playerService

import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import com.martini.dartplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartNewSessionUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(
        params: StartNewSessionParams
    ) = flow {
        try {
            emit(StartNewSessionState.Loading)
            playerRepository.startNewSession(params)
            emit(StartNewSessionState.Loaded)
        } catch (e: Exception) {
            emit(StartNewSessionState.Failure)
        }
    }
}

sealed class StartNewSessionState {
    object Loading : StartNewSessionState()
    object Loaded : StartNewSessionState()
    object Failure : StartNewSessionState()
}
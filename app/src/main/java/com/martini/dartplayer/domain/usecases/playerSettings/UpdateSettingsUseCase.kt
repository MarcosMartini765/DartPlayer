package com.martini.dartplayer.domain.usecases.playerSettings

import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePlayerSettingsUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    val listen = MutableSharedFlow<UpdatePlayerSettingsState>(0)

    operator fun invoke(settings: PlayerSettings) = flow {
        try {
            playerRepository.insertSettings(settings)
            emit(UpdatePlayerSettingsState.Loaded(settings))
            listen.emit(UpdatePlayerSettingsState.Loaded(settings))
        } catch (e: Exception) {
            listen.emit(UpdatePlayerSettingsState.Failure)
            emit(UpdatePlayerSettingsState.Failure)
        }
    }

    fun updateRepeatMode(settings: PlayerSettings) = flow {
        try {
            playerRepository.insertSettings(settings)
            emit(UpdatePlayerSettingsState.UpdateRepeatMode(settings))
            listen.emit(UpdatePlayerSettingsState.UpdateRepeatMode(settings))
        } catch (e: Exception) {
            listen.emit(UpdatePlayerSettingsState.Failure)
            emit(UpdatePlayerSettingsState.Failure)
        }
    }
}

sealed class UpdatePlayerSettingsState {
    object Initial : UpdatePlayerSettingsState()
    object Loading : UpdatePlayerSettingsState()
    class Loaded(
        val settings: PlayerSettings
    ) : UpdatePlayerSettingsState()

    class UpdateRepeatMode(
        val settings: PlayerSettings
    ) : UpdatePlayerSettingsState()

    object Failure : UpdatePlayerSettingsState()
}
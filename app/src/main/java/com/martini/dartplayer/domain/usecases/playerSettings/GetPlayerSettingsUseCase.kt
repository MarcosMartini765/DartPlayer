package com.martini.dartplayer.domain.usecases.playerSettings

import android.util.Log
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlayerSettingsUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    val listen = MutableSharedFlow<GetPlayerSettingsState>(0)

    operator fun invoke() = flow {
        try {
            emit(GetPlayerSettingsState.Loading)
            listen.emit(GetPlayerSettingsState.Loading)
            val settings = playerRepository.getSettings()
            emit(GetPlayerSettingsState.Loaded(settings))
            listen.emit(GetPlayerSettingsState.Loaded(settings))
        } catch (e: Exception) {
            Log.d("MUSIC_MODEL", e.stackTraceToString())
            emit(GetPlayerSettingsState.Failure)
            listen.emit(GetPlayerSettingsState.Failure)
        }
    }
}

sealed class GetPlayerSettingsState {
    object Initial : GetPlayerSettingsState()
    object Loading : GetPlayerSettingsState()
    class Loaded(
        val settings: PlayerSettings
    ) : GetPlayerSettingsState()

    object Failure : GetPlayerSettingsState()
}
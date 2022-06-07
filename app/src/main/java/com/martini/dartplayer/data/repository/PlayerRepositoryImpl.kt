package com.martini.dartplayer.data.repository

import com.martini.dartplayer.data.local.PlayerControlsApi
import com.martini.dartplayer.data.local.PlayerSettingsApi
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.domain.params.player.StartNewSessionParams
import com.martini.dartplayer.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerSettingsApi: PlayerSettingsApi,
    private val playerControlsApi: PlayerControlsApi
) : PlayerRepository {
    override suspend fun getSettings(): PlayerSettings {
        return playerSettingsApi.getSettings()
    }

    override suspend fun insertSettings(settings: PlayerSettings) {
        return playerSettingsApi.insertSettings(settings)
    }

    override suspend fun startNewSession(startNewSessionParams: StartNewSessionParams) {
        playerControlsApi.startNewSession(startNewSessionParams)
    }

    override suspend fun playNext(playNextParams: PlayNextParams) {
        playerControlsApi.playNext(playNextParams)
    }
}
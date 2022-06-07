package com.martini.dartplayer.domain.repository

import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.domain.params.player.StartNewSessionParams

interface PlayerRepository {
    suspend fun getSettings(): PlayerSettings

    suspend fun insertSettings(settings: PlayerSettings)

    suspend fun startNewSession(startNewSessionParams: StartNewSessionParams)
    suspend fun playNext(playNextParams: PlayNextParams)
}
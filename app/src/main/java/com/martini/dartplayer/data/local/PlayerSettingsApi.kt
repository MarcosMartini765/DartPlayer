package com.martini.dartplayer.data.local

import com.martini.dartplayer.domain.entity.daos.PlayerDao
import com.martini.dartplayer.domain.entity.player.PlaybackMode
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import javax.inject.Inject

class PlayerSettingsApi @Inject constructor(
    private val dao: PlayerDao
) {
    suspend fun getSettings(): PlayerSettings {
        return dao.getSettings()
            ?: PlayerSettings(
                playbackMode = PlaybackMode.Loop,
                respectAudioFocus = true
            )
    }

    suspend fun insertSettings(settings: PlayerSettings) {
        dao.insertSettings(settings)
    }
}
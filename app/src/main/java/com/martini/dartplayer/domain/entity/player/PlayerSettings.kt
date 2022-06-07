package com.martini.dartplayer.domain.entity.player

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayerSettings(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    val playbackMode: PlaybackMode,
    val respectAudioFocus: Boolean
)

fun PlayerSettings.copyWith(
    playbackMode: PlaybackMode = this.playbackMode,
    respectAudioFocus: Boolean = this.respectAudioFocus
): PlayerSettings {
    return PlayerSettings(
        playbackMode = playbackMode,
        respectAudioFocus = respectAudioFocus
    )
}
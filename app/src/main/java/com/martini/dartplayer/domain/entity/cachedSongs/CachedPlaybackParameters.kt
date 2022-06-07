package com.martini.dartplayer.domain.entity.cachedSongs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedPlaybackParameters(
    @PrimaryKey(autoGenerate = false) val id: Long = 1,
    val index: Long,
    val position: Long,
    val shuffled: Boolean
)

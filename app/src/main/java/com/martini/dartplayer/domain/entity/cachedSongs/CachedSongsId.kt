package com.martini.dartplayer.domain.entity.cachedSongs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedSongsId(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val songIndex: Int,
    val queryIndex: Int
)

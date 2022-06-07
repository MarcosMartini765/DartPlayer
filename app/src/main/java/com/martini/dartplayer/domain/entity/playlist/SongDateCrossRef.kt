package com.martini.dartplayer.domain.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SongDateCrossRef(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val dateAdded: Long = 0,
    val playlist: String
)
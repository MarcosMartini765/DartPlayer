package com.martini.dartplayer.domain.entity.album

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @PrimaryKey(autoGenerate = false) val albumName: String
)

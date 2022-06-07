package com.martini.dartplayer.domain.entity.artist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artist(
    @PrimaryKey(autoGenerate = false) val artistName: String
)

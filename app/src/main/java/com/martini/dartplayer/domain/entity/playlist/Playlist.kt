package com.martini.dartplayer.domain.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = false)
    val playlistName: String
)
package com.martini.dartplayer.domain.entity.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["playlistName", "id"])
data class PlaylistSongCrossRef(
    val playlistName: String,
    @ColumnInfo(name="id", index = true)
    val id: Long
)
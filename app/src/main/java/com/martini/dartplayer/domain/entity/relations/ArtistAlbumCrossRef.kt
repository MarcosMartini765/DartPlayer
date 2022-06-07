package com.martini.dartplayer.domain.entity.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["artistName", "albumName"])
data class ArtistAlbumCrossRef(
    val artistName: String,
    @ColumnInfo(name="albumName", index = true)
    val albumName: String
)

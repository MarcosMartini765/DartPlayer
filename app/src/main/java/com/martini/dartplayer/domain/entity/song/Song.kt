package com.martini.dartplayer.domain.entity.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo val artist: String,
    @ColumnInfo val album: String,
    @ColumnInfo val duration: Long,
    @ColumnInfo val uri: String,
    @ColumnInfo val imageUri: String?,
    @ColumnInfo val dateModified: Long,
    @ColumnInfo val dateAdded: Long
)

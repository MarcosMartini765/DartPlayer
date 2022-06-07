package com.martini.dartplayer.domain.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.song.Song

data class AlbumWithSongs(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "albumName",
        entityColumn = "album"
    )
    val songs: List<Song>
)

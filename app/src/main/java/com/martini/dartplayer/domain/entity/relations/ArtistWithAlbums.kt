package com.martini.dartplayer.domain.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.artist.Artist

data class ArtistWithAlbums(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "artistName",
        entityColumn = "albumName",
        associateBy = Junction(ArtistAlbumCrossRef::class)
    )
    val albums: List<Album>
)

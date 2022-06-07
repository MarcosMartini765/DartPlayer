package com.martini.dartplayer.domain.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.artist.Artist

data class AlbumWithArtists(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "albumName",
        entityColumn = "artistName",
        associateBy = Junction(ArtistAlbumCrossRef::class)
    )
    val artists: List<Artist>
)

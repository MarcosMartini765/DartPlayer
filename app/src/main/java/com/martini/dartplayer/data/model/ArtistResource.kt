package com.martini.dartplayer.data.model

import com.martini.dartplayer.domain.entity.artist.Artist

data class ArtistResource(
    val artistName: String
)


fun ArtistResource.toArtist(): Artist {
    return Artist(artistName)
}

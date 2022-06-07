package com.martini.dartplayer.domain.entity.artist

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs

data class ArtistWithAlbumsAndSongs(
    val name: String,
    val albums: List<AlbumWithSongs>,
)

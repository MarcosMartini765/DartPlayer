package com.martini.dartplayer.domain.params.artist

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs

class PlayArtistParams(
    val albums: List<AlbumWithSongs>,
    val shuffled: Boolean
)

class GetSongsForArtistParams(
    val artist: String,
    val shuffled: Boolean
)
package com.martini.dartplayer.domain.params.albumForArtist

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs

class DeleteAlbumForArtistParams(
    val albumWithSongs: AlbumWithSongs,
    val artistName: String
)
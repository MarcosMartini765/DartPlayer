package com.martini.dartplayer.domain.entity.album

import com.martini.dartplayer.domain.entity.relations.AlbumWithArtists
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs

data class AlbumWithSongAndArtists(
    val songs: AlbumWithSongs,
    val artists: AlbumWithArtists
)

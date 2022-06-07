package com.martini.dartplayer.domain.entity

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song

data class Music(
    val songs: List<Song>,
    val albums: List<AlbumWithSongs>,
    val artists: List<ArtistWithAlbums>,
    val playlists: List<PlaylistWithSongs>
)

package com.martini.dartplayer.domain.params.player

import com.martini.dartplayer.domain.entity.song.Song

class GetCachedPlaylistParams(
    val songs: List<Song>,
)
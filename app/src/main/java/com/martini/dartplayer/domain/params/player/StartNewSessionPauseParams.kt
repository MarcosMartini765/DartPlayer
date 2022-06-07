package com.martini.dartplayer.domain.params.player

import com.martini.dartplayer.domain.entity.song.Song

class StartNewSessionPausedParams(
    val position: Long,
    val index: Int,
    val songs: List<Song>
)
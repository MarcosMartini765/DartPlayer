package com.martini.dartplayer.domain.params.dashboard

import com.martini.dartplayer.domain.entity.song.Song

class PlaySongAtIndexParams(
    val songs: List<Song>,
    val index: Int = 0,
    val shuffled: Boolean = false
)
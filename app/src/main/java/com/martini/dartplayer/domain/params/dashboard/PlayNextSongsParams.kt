package com.martini.dartplayer.domain.params.dashboard

import com.martini.dartplayer.domain.entity.song.Song

class PlayNextSongsParams(
    val songs: List<Song>,
    val shuffled: Boolean
)
package com.martini.dartplayer.domain.params.player

import com.google.android.exoplayer2.Timeline

class SetCachedPlaylistParams(
    val timeline: Timeline,
    val shuffled: Boolean,
    val repeat: Int
)
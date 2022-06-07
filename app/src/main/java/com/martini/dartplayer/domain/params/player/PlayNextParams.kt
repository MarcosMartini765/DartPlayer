package com.martini.dartplayer.domain.params.player

import com.martini.dartplayer.domain.entity.Selected

class PlayNextParams(
    val selected: Selected,
    val shuffled: Boolean = false,
)
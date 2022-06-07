package com.martini.dartplayer.domain.params.player

import com.martini.dartplayer.domain.entity.Selected

class StartNewSessionParams(
    val selected: Selected,
    val shuffled: Boolean = false,
    val index: Int = 0
)
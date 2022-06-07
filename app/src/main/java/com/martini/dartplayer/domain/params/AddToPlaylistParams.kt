package com.martini.dartplayer.domain.params

import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.playlist.Playlist

class AddToPlaylistParams(
    val selected: Selected,
    val playlist: Playlist
)
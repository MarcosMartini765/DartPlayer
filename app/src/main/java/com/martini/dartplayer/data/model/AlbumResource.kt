package com.martini.dartplayer.data.model

import com.martini.dartplayer.domain.entity.album.Album

data class AlbumResource(
    val name: String,
)

fun AlbumResource.toAlbum(): Album {
    return Album(name)
}
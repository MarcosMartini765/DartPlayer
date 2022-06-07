package com.martini.dartplayer.data.model

import android.net.Uri
import com.martini.dartplayer.domain.entity.song.Song

data class SongResource(
    val id: Long,
    val name: String,
    val artist: String,
    val albumArtist: String,
    val album: String,
    val genre: String,
    val duration: Long,
    val uri: Uri,
    val imageUri: String?,
    val dateModified: Long,
    val dateAdded: Long
)

fun SongResource.toSong() : Song {
    return Song(
        id,
        name,
        artist,
        album,
        duration,
        uri.toString(),
        imageUri,
        dateModified,
        dateAdded
    )
}
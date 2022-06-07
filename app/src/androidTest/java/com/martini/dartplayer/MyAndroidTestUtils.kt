package com.martini.dartplayer

import com.github.javafaker.Faker
import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.artist.Artist
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.song.Song
import java.util.*

class MyAndroidTestUtils {

    private val faker = Faker(Locale.US)

    fun createSong(
        id: Long? = null,
        name: String? = null,
        artist: String? = null,
        album: String? = null,
        dateAdded: Long? = null,
        dateModified: Long? = null,
        duration: Long? = null,
        uri: String? = null,
        imageUri: String? = null
    ): Song {
        return Song(
            id = id ?: faker.number().randomNumber(),
            name = name ?: faker.name().firstName(),
            artist = artist ?: faker.name().lastName(),
            album = album ?: faker.name().fullName(),
            dateAdded = dateAdded ?: faker.number().randomNumber(),
            dateModified = dateModified ?: faker.number().randomNumber(),
            duration = duration ?: faker.number().randomNumber(),
            uri = uri ?: faker.address().city(),
            imageUri = imageUri ?: faker.address().cityName()
        )
    }

    fun createArtist(
        name: String? = null
    ): Artist {
        return Artist(
            artistName = name ?: faker.name().firstName()
        )
    }

    fun createAlbum(
        name: String? = null
    ): Album {
        return Album(
            albumName = name ?: faker.name().firstName()
        )
    }
}
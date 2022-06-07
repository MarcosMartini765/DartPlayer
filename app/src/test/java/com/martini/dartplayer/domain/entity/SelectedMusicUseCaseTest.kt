package com.martini.dartplayer.domain.entity

import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.artist.Artist
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SelectedMusicUseCaseTest {

    private val song = Song(
        45,
        "name",
        "artist",
        "album",
        200,
        "uri",
        "uri",
        200,
        150
    )

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun addSongIsCorrect() = runBlocking {
        val selectedMusicUseCase = SelectedMusicUseCase()
        val song = Song(
            45,
            "name",
            "artist",
            "album",
            200,
            "uri",
            "uri",
            200,
            150
        )

        val expectedValue = Selected(listOf(song))

        selectedMusicUseCase.addSong(song).first()
        val result = selectedMusicUseCase.addSong(song)
            .first()
        Assert.assertEquals(result.music, expectedValue.music)
    }

    @Test
    fun addSongOrRemoveIsCorrect() = runBlocking {
        val selectedMusicUseCase = SelectedMusicUseCase()

        val expectedValue = Selected(emptyList())

        selectedMusicUseCase.addOrRemoveSong(song).first()
        val result = selectedMusicUseCase.addOrRemoveSong(song)
            .first()
        Assert.assertEquals(result.music, expectedValue.music)
    }

    @Test
    fun addOrRemoveAlbumIsCorrect() = runBlocking {
        val selectedMusicUseCase = SelectedMusicUseCase()
        val album = AlbumWithSongs(
            album = Album("name"),
            songs = listOf(song)
        )
        val expectedResult = Selected(listOf(album))

        selectedMusicUseCase.addOrRemoveAlbum(album).first()
        selectedMusicUseCase.addOrRemoveAlbum(album).first()

        val result = selectedMusicUseCase.addOrRemoveAlbum(album).first()

        Assert.assertEquals(result.music, expectedResult.music)
    }

    @Test
    fun addOrRemoveArtistIsCorrect() = runBlocking {
        val selectedMusicUseCase = SelectedMusicUseCase()
        val album = Album("name")
        val artist = ArtistWithAlbums(
            artist = Artist("name"),
            albums = listOf(album)
        )
        val expectedValue = Selected(listOf(artist))

        selectedMusicUseCase.addOrRemoveArtist(artist).first()
        selectedMusicUseCase.addOrRemoveArtist(artist).first()

        val result = selectedMusicUseCase.addOrRemoveArtist(artist).first()

        Assert.assertEquals(result.music, expectedValue.music)
    }

    @Test
    fun addOrRemovePlaylistIsCorrect() = runBlocking {
        val selectedMusicUseCase = SelectedMusicUseCase()
        val playlist = PlaylistWithSongs(
            playlist = Playlist("name"),
            songs = listOf(song)
        )
        val expectedValue = Selected(listOf(playlist))

        selectedMusicUseCase.addOrRemovePlaylist(playlist).first()
        selectedMusicUseCase.addOrRemovePlaylist(playlist).first()

        val result = selectedMusicUseCase.addOrRemovePlaylist(playlist).first()

        Assert.assertEquals(result.music, expectedValue.music)
    }

    @Test
    fun clearIsCorrect() = runBlocking {
        val selectedMusicUseCase = SelectedMusicUseCase()

        selectedMusicUseCase.addSong(song).first()

        val expectedValue = Selected(emptyList())

        val result = selectedMusicUseCase.clear().first()

        Assert.assertEquals(result.music, expectedValue.music)
    }
}
package com.martini.dartplayer.domain.usecases

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NavigationUseCaseTest {
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
    fun navigateToAddPlaylistIsCorrect() = runBlocking {
        val navigationUseCase = NavigationUseCase()

        launch(Dispatchers.Main) {
            navigationUseCase.navigateToAddPlaylist().launchIn(this)
        }
        val result = navigationUseCase.listen.first()

        val expected = NavigationAction.NavigateToAddPlaylist

        Assert.assertEquals(result, expected)
    }

    @Test
    fun navigateToAddArtistIsCorrect() = runBlocking {
        val navigationUseCase = NavigationUseCase()
        val name = "name"

        navigationUseCase.navigateToArtist(name).launchIn(this)

        val result = navigationUseCase.listen.first()

        val expected = NavigationAction.NavigateToArtist(name)

        Assert.assertTrue(result is NavigationAction.NavigateToArtist && result.name == expected.name)
    }

    @Test
    fun navigateToAlbumForArtistIsCorrect() = runBlocking {
        val navigationUseCase = NavigationUseCase()
        val album = "album"
        val artist = "artist"

        navigationUseCase.navigateToAlbumForArtist(album, artist).launchIn(this)

        val result = navigationUseCase.listen.first()

        val expected = NavigationAction.NavigateToAlbumForArtist(album, artist)

        Assert.assertTrue(
            result is NavigationAction.NavigateToAlbumForArtist
                    && result.album == expected.album
                    && result.artist == expected.artist
        )
    }

    @Test
    fun navigateToAlbumIsCorrect() = runBlocking {
        val navigationUseCase = NavigationUseCase()
        val name = "name"

        navigationUseCase.navigateToAlbum(name).launchIn(this)

        val result = navigationUseCase.listen.first()

        val expected = NavigationAction.NavigateToAlbum(name)

        Assert.assertTrue(result is NavigationAction.NavigateToAlbum && result.name == expected.name)
    }

    @Test
    fun navigateToSettingsIsCorrect() = runBlocking {
        val navigationUseCase = NavigationUseCase()

        navigationUseCase.navigateToSettings().launchIn(this)

        val result = navigationUseCase.listen.first()

        val expected = NavigationAction.NavigateToSettings

        Assert.assertTrue(result is NavigationAction.NavigateToSettings)
    }
}
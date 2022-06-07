package com.martini.dartplayer.data.local.musicManager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.martini.dartplayer.AppDatabase
import com.martini.dartplayer.MyAndroidTestUtils
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.relations.ArtistAlbumCrossRef
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetArtistsForAlbumApiTest {
    private lateinit var db: AppDatabase
    private lateinit var musicDao: MusicDao
    private lateinit var getArtistForAlbumApi: GetArtistsForAlbumApi
    private val utils = MyAndroidTestUtils()

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        musicDao = db.musicDao()

        getArtistForAlbumApi = GetArtistsForAlbumApi(musicDao)
    }

    @After
    fun dispose() {
        db.close()
    }

    @Test
    fun shouldReturnTwoArtists() = runBlocking {

        val artists = listOf(
            utils.createArtist(),
            utils.createArtist()
        )

        val album = utils.createAlbum()

        val albums = listOf(
            utils.createAlbum(),
            album
        )

        val crossRef = mutableListOf<ArtistAlbumCrossRef>()

        for (artist in artists) {
            for (alb in albums) {
                crossRef += ArtistAlbumCrossRef(
                    artistName = artist.artistName,
                    albumName = alb.albumName
                )
            }
        }

        musicDao.insertArtists(artists)
        musicDao.insertAlbums(albums)
        musicDao.insertListOfArtistAlbumCrossRef(crossRef)

        val result = getArtistForAlbumApi(album.albumName)

        Assert.assertTrue(result.count() == 2)
    }

    @Test
    fun shouldReturnNoArtists() = runBlocking {

        val artists = listOf(
            utils.createArtist(),
            utils.createArtist()
        )

        val album = utils.createAlbum()

        val albums = listOf(
            utils.createAlbum(),
            album
        )

        val crossRef = mutableListOf<ArtistAlbumCrossRef>()

        for (artist in artists) {
            for (alb in albums) {
                crossRef += ArtistAlbumCrossRef(
                    artistName = artist.artistName,
                    albumName = alb.albumName
                )
            }
        }

        val ghostAlbum = utils.createAlbum()

        musicDao.insertArtists(artists)
        musicDao.insertAlbums(albums)
        musicDao.insertAlbums(listOf(ghostAlbum))
        musicDao.insertListOfArtistAlbumCrossRef(crossRef)

        val result = getArtistForAlbumApi(ghostAlbum.albumName)

        Assert.assertTrue(result.isEmpty())
    }
}
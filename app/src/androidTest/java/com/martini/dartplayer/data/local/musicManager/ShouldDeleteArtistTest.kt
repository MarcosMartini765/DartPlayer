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
class ShouldDeleteArtistTest {
    private lateinit var db: AppDatabase
    private lateinit var musicDao: MusicDao
    private lateinit var shouldDeleteArtist: ShouldDeleteArtist
    private val utils = MyAndroidTestUtils()

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        musicDao = db.musicDao()

        shouldDeleteArtist = ShouldDeleteArtist(musicDao)
    }

    @After
    fun dispose() {
        db.close()
    }

    @Test
    fun shouldReturnFalse() = runBlocking {
        val artists = listOf(
            utils.createArtist()
        )

        val albums = listOf(
            utils.createAlbum(),
            utils.createAlbum(),
            utils.createAlbum(),
        )

        val crossRefs = mutableListOf<ArtistAlbumCrossRef>()

        for (artist in artists) {
            for (album in albums) {
                crossRefs += ArtistAlbumCrossRef(
                    artistName = artist.artistName,
                    albumName = album.albumName
                )
            }
        }

        musicDao.insertArtists(artists)
        musicDao.insertAlbums(albums)
        musicDao.insertListOfArtistAlbumCrossRef(crossRefs)

        val should = shouldDeleteArtist(artists.first().artistName)

        Assert.assertFalse(should)
    }

    @Test
    fun shouldReturnTrue() = runBlocking {
        val artists = listOf(
            utils.createArtist()
        )

        val albums = listOf(
            utils.createAlbum(),
            utils.createAlbum(),
            utils.createAlbum(),
        )

        val crossRefs = mutableListOf<ArtistAlbumCrossRef>()

        for (artist in artists) {
            for (album in albums) {
                crossRefs += ArtistAlbumCrossRef(
                    artistName = artist.artistName,
                    albumName = album.albumName
                )
            }
        }

        musicDao.insertArtists(artists)
        musicDao.insertAlbums(albums)

        val should = shouldDeleteArtist(artists.first().artistName)

        Assert.assertTrue(should)
    }

}
package com.martini.dartplayer.data.local.musicManager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.martini.dartplayer.AppDatabase
import com.martini.dartplayer.MyAndroidTestUtils
import com.martini.dartplayer.domain.entity.daos.MusicDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShouldDeleteAlbumTest {
    private lateinit var db: AppDatabase
    private lateinit var musicDao: MusicDao
    private lateinit var shouldDeleteAlbum: ShouldDeleteAlbum
    private val utils = MyAndroidTestUtils()

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        musicDao = db.musicDao()

        shouldDeleteAlbum = ShouldDeleteAlbum(musicDao)
    }

    @After
    fun dispose() {
        db.close()
    }

    @Test
    fun shouldReturnFalse() = runBlocking {

        val albumName = "name"

        val songs = listOf(
            utils.createSong(album = albumName),
            utils.createSong(album = albumName),
            utils.createSong(album = albumName),
        )

        val album = utils.createAlbum(name = albumName)

        musicDao.insertSongs(songs)
        musicDao.insertAlbums(listOf(album))

        val should = shouldDeleteAlbum(album.albumName)

        Assert.assertFalse(should)
    }

    @Test
    fun shouldReturnTrue() = runBlocking {

        val albumName = "name"

        val album = utils.createAlbum(name = albumName)

        musicDao.insertAlbums(listOf(album))

        val should = shouldDeleteAlbum(album.albumName)

        Assert.assertTrue(should)
    }
}
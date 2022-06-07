package com.martini.dartplayer.data.local.musicManager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.martini.dartplayer.AppDatabase
import com.martini.dartplayer.MyAndroidTestUtils
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.playlist.SongDateCrossRef
import com.martini.dartplayer.domain.entity.relations.PlaylistSongCrossRef
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeleteSongApiTest {

    private lateinit var db: AppDatabase
    private lateinit var musicDao: MusicDao
    private lateinit var playlistDao: PlaylistDao
    private lateinit var deleteSongApi: DeleteSongApi
    private val utils = MyAndroidTestUtils()

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        musicDao = db.musicDao()
        playlistDao = db.playlistDao()

        deleteSongApi = DeleteSongApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @After
    fun dispose() {
        db.close()
    }

    @Test
    fun isCorrect() = runBlocking {

        val playlistName = "name"
        val song = utils.createSong()

        val songs = listOf(
            song,
            utils.createSong()
        )

        val playlistSongCrossRef = PlaylistSongCrossRef(
            playlistName = playlistName,
            id = song.id
        )

        val songDateCrossRef = SongDateCrossRef(
            id = song.id,
            dateAdded = System.currentTimeMillis(),
            playlist = playlistName
        )

        playlistDao.createPlaylist(Playlist(playlistName))
        musicDao.insertSongs(songs)
        playlistDao.insertCrossRefs(listOf(playlistSongCrossRef))
        playlistDao.insertSongDateCrossRefs(listOf(songDateCrossRef))

        deleteSongApi(song)

        val found: Boolean = try {
            val id = musicDao.getSongForId(song.id)
            id.id == song.id
        } catch (e: Exception) {
            false
        }

        val empty = playlistDao.getPlaylist(playlistName).songs.isEmpty()

        Assert.assertTrue(!found && empty)
    }
}
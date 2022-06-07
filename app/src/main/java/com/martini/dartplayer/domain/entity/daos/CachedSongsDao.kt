package com.martini.dartplayer.domain.entity.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.entity.cachedSongs.CachedSongsId
import com.martini.dartplayer.domain.entity.song.Song

@Dao
interface CachedSongsDao {
    @Insert
    suspend fun insertAll(cachedSongsId: List<CachedSongsId>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateParameters(params: CachedPlaybackParameters)

    @Query("SELECT * FROM song WHERE song.id == :id")
    suspend fun getSongById(id: Long): Song?

    @Query("SELECT * FROM song")
    suspend fun getAllSongs(): List<Song>

    @Query("DELETE FROM cachedsongsid")
    suspend fun clear()

    @Query("SELECT * FROM cachedplaybackparameters WHERE id = 1")
    suspend fun getParams(): CachedPlaybackParameters

    @Query("SELECT * FROM cachedsongsid ORDER BY queryIndex")
    suspend fun getPlaylist(): List<CachedSongsId>
}
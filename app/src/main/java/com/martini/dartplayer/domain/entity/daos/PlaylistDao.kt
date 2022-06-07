package com.martini.dartplayer.domain.entity.daos

import androidx.room.*
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.playlist.SongDateCrossRef
import com.martini.dartplayer.domain.entity.relations.PlaylistSongCrossRef
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createPlaylist(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRefs(crossRefs: List<PlaylistSongCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongDateCrossRefs(refs: List<SongDateCrossRef>)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Delete
    suspend fun deleteCrossRefs(playlistSongCrossRef: List<PlaylistSongCrossRef>)

    @Delete
    suspend fun deleteSongDateCrossRefs(refs: List<SongDateCrossRef>)

    @Query("DELETE FROM playlistsongcrossref WHERE id IN (:ids)")
    suspend fun deleteSongsFromPlaylists(ids: List<Long>)

    @Transaction
    @Query("SELECT * FROM playlist ORDER BY playlistName")
    suspend fun getAllPlaylists(): List<PlaylistWithSongs>

    @Transaction
    @Query("SELECT * FROM playlist ORDER BY playlistName DESC")
    suspend fun getAllPlaylistsDesc(): List<PlaylistWithSongs>

    @Transaction
    @Query("SELECT * FROM playlist WHERE playlistName = :name")
    suspend fun getPlaylist(name: String) : PlaylistWithSongs

    @Query("DELETE FROM playlistsongcrossref WHERE playlistName = :name")
    suspend fun deleteCrossForPlaylist(name: String)

    @Query("DELETE FROM songdatecrossref WHERE playlist = :name")
    suspend fun deleteSongDateCrossRefForPlaylist(name: String)

    @Query("SELECT * FROM songdatecrossref WHERE playlist = :name ORDER BY dateAdded DESC")
    suspend fun getCrossRefsForPlaylist(name: String): List<SongDateCrossRef>
}
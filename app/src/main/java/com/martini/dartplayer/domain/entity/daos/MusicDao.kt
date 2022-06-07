package com.martini.dartplayer.domain.entity.daos

import androidx.room.*
import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.artist.Artist
import com.martini.dartplayer.domain.entity.relations.AlbumWithArtists
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistAlbumCrossRef
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.song.Song

@Dao
interface MusicDao {

    //Songs
    @Query("SELECT * FROM song ORDER BY name ASC")
    suspend fun getAllSongs(): List<Song>

    @Query("SELECT * FROM song ORDER BY name DESC")
    suspend fun getAllSongsDesc(): List<Song>

    @Query("SELECT * FROM song ORDER BY dateAdded DESC")
    suspend fun getAllSongsDateAdd(): List<Song>

    @Query("SELECT * FROM song WHERE album == :album AND artist == :artist")
    suspend fun getSongsForAlbumAndArtist(album: String, artist: String): List<Song>

    @Query("SELECT * FROM song WHERE id == :id")
    suspend fun getSongForId(id: Long): Song

    @Query("DELETE FROM artist WHERE artistName = :name")
    suspend fun deleteArtist(name: String)

    @Transaction
    @Query(
        "" +
                "SELECT * FROM song " +
                "WHERE song.name LIKE  '%' || :text || '%' COLLATE utf8_general_ci " +
                "OR song.artist LIKE '%' || :text || '%' COLLATE utf8_general_ci " +
                "OR song.album LIKE '%' || :text || '%' COLLATE utf8_general_ci " +
                "ORDER BY song.name ASC"
    )
    suspend fun searchSongs(text: String): List<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<Song>)

    @Delete
    suspend fun deleteSongs(songs: List<Song>)
    //Songs


    //Artists
    @Transaction
    @Query("SELECT * FROM artist ORDER BY artistName ASC")
    suspend fun getArtistsWithAlbums(): List<ArtistWithAlbums>

    @Transaction
    @Query("SELECT * FROM artist ORDER BY artistName DESC")
    suspend fun getArtistsWithAlbumsDesc(): List<ArtistWithAlbums>

    @Transaction
    @Query("SELECT * FROM artist WHERE artistName == :artist")
    suspend fun getArtistWithAlbums(artist: String): ArtistWithAlbums

    @Query("SELECT * FROM artist")
    suspend fun getAllArtists(): List<Artist>

    @Transaction
    @Query(
        "SELECT * FROM artist " +
                "WHERE artistName LIKE '%' || :text || '%' COLLATE utf8_general_ci " +
                "ORDER BY artistName ASC"
    )
    suspend fun searchArtists(text: String): List<ArtistWithAlbums>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtists(artists: List<Artist>)

    @Delete
    suspend fun deleteArtists(artists: List<Artist>)
    //Artists


    //Albums
    @Transaction
    @Query("SELECT * FROM album ORDER BY albumName ASC")
    suspend fun getAlbumsWithSongs(): List<AlbumWithSongs>

    @Transaction
    @Query("SELECT * FROM album ORDER BY albumName DESC")
    suspend fun getAlbumsWithSongsDesc(): List<AlbumWithSongs>

    @Transaction
    @Query("SELECT * FROM album WHERE albumName == :album")
    suspend fun getAlbumWithSongs(album: String): AlbumWithSongs


    @Transaction
    @Query("SELECT * FROM album WHERE albumName == :album")
    suspend fun getAlbumWithArtists(album: String): AlbumWithArtists

    @Query("SELECT * FROM album WHERE albumName == :albumName")
    suspend fun getAlbum(albumName: String): Album

    @Query("SELECT * FROM album")
    suspend fun getAllAlbums(): List<Album>

    @Query("DELETE FROM artistalbumcrossref WHERE albumName = :albumName")
    suspend fun deleteAlbumArtistCrossRefs(albumName: String)

    @Transaction
    @Query(
        "SELECT * FROM album " +
                "WHERE albumName LIKE '%' || :text || '%' COLLATE utf8_general_ci " +
                "ORDER BY albumName"
    )
    suspend fun searchAlbums(text: String): List<AlbumWithSongs>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbums(albums: List<Album>)

    @Delete
    suspend fun deleteAlbums(albums: List<Album>)
    //Albums


    //ArtistAlbumCrossRef
    @Query("SELECT * from artistalbumcrossref")
    suspend fun getAllArtistAlbumCrossRefs(): List<ArtistAlbumCrossRef>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListOfArtistAlbumCrossRef(crossRef: List<ArtistAlbumCrossRef>)

    @Delete
    suspend fun deleteListOfArtistAlbumCrossRef(crossRef: List<ArtistAlbumCrossRef>)
    //ArtistAlbumCrossRef
}
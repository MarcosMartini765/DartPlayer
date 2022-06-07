package com.martini.dartplayer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.martini.dartplayer.domain.entity.album.Album
import com.martini.dartplayer.domain.entity.artist.Artist
import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.entity.cachedSongs.CachedSongsId
import com.martini.dartplayer.domain.entity.daos.CachedSongsDao
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlayerDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.files.LocalFilesDao
import com.martini.dartplayer.domain.entity.files.LocalFilesSettings
import com.martini.dartplayer.domain.entity.player.PlayerSettings
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.playlist.SongDateCrossRef
import com.martini.dartplayer.domain.entity.relations.ArtistAlbumCrossRef
import com.martini.dartplayer.domain.entity.relations.PlaylistSongCrossRef
import com.martini.dartplayer.domain.entity.song.Song

@Database(
    entities = [
        Song::class,
        Album::class,
        Artist::class,
        ArtistAlbumCrossRef::class,
        LocalFilesSettings::class,
        CachedSongsId::class,
        CachedPlaybackParameters::class,
        PlayerSettings::class,
        Playlist::class,
        PlaylistSongCrossRef::class,
        SongDateCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao

    abstract fun localFilesDao(): LocalFilesDao

    abstract fun cachedSongsDao(): CachedSongsDao

    abstract fun playerDao(): PlayerDao

    abstract fun playlistDao(): PlaylistDao
}
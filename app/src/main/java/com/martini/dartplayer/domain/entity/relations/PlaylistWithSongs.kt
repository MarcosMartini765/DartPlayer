package com.martini.dartplayer.domain.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.martini.dartplayer.domain.entity.playlist.Playlist
import com.martini.dartplayer.domain.entity.song.Song


data class PlaylistWithSongs(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "playlistName",
        entityColumn = "id",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val songs: List<Song>
)
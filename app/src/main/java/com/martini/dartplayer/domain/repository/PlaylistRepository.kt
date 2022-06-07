package com.martini.dartplayer.domain.repository

import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.params.AddToPlaylistParams

interface PlaylistRepository {
    suspend fun addToPlaylist(params: AddToPlaylistParams)

    suspend fun createPlaylist(name: String)

    suspend fun getPlaylist(name: String): PlaylistWithSongs

    suspend fun removeSongFromPlaylist(selected: Selected, playlist: PlaylistWithSongs)

    suspend fun deletePlaylist(playlist: PlaylistWithSongs)
}
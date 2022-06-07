package com.martini.dartplayer.data.repository

import com.martini.dartplayer.data.local.PlaylistApi
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.params.AddToPlaylistParams
import com.martini.dartplayer.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistApi: PlaylistApi
) : PlaylistRepository {
    override suspend fun addToPlaylist(params: AddToPlaylistParams) {
        return playlistApi.addToPlaylist(params)
    }

    override suspend fun createPlaylist(name: String) {
        return playlistApi.createPlaylist(name)
    }

    override suspend fun getPlaylist(name: String): PlaylistWithSongs {
        return playlistApi.getPlaylist(name)
    }

    override suspend fun removeSongFromPlaylist(selected: Selected, playlist: PlaylistWithSongs) {
        return playlistApi.removeSongFromPlaylist(selected, playlist)
    }

    override suspend fun deletePlaylist(playlist: PlaylistWithSongs) {
        return playlistApi.deletePlaylist(playlist)
    }
}
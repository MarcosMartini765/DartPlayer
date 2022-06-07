package com.martini.dartplayer.domain.usecases.toDelete

import com.martini.dartplayer.data.local.LocalMusicApi
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePlaylistsUseCase @Inject constructor(
    private val localMusicApi: LocalMusicApi
) {
    operator fun invoke(playlists: List<PlaylistWithSongs>) = flow {
        try {
            emit(DeletePlaylistsState.Loading)
            localMusicApi.deleteMusic(Selected(playlists))
            emit(DeletePlaylistsState.Loaded)
        } catch (e: Exception) {
            emit(DeletePlaylistsState.Failure)
        }
    }
}

sealed class DeletePlaylistsState {
    object Loading : DeletePlaylistsState()
    object Loaded : DeletePlaylistsState()
    object Failure : DeletePlaylistsState()
}
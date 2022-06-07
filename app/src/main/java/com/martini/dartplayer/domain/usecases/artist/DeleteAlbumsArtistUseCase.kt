package com.martini.dartplayer.domain.usecases.artist

import com.martini.dartplayer.domain.entity.artist.ArtistWithAlbumsAndSongs
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAlbumsArtistUseCase @Inject constructor(
    val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<DeleteAlbumsArtistState>(DeleteAlbumsArtistState.Initial)

    operator fun invoke(
        artist: ArtistWithAlbumsAndSongs,
        albums: List<AlbumWithSongs>
    ): Flow<DeleteAlbumsArtistState> = flow {
        try {
            emit(DeleteAlbumsArtistState.Loading)
            listen.emit(DeleteAlbumsArtistState.Loading)
            musicRepository.deleteAlbumsForArtist(artist, albums)
            emit(DeleteAlbumsArtistState.Loaded)
            listen.emit(DeleteAlbumsArtistState.Loaded)
        } catch (e: Exception) {
            emit(DeleteAlbumsArtistState.Failure)
            listen.emit(DeleteAlbumsArtistState.Failure)
        }
    }
}

sealed class DeleteAlbumsArtistState {
    object Initial : DeleteAlbumsArtistState()
    object Loaded : DeleteAlbumsArtistState()
    object Loading : DeleteAlbumsArtistState()
    object Failure : DeleteAlbumsArtistState()
}
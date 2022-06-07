package com.martini.dartplayer.domain.usecases.albumForArtist

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.params.albumForArtist.LoadAlbumForArtistParams
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadAlbumForArtistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen = MutableStateFlow<LoadAlbumForArtistState>(LoadAlbumForArtistState.Initial)

    operator fun invoke(
        loadAlbumForArtistParams: LoadAlbumForArtistParams
    ) = flow {
        try {
            emit(LoadAlbumForArtistState.Loading)
            listen.emit(LoadAlbumForArtistState.Loading)
            val album = musicRepository.loadAlbumForArtist(loadAlbumForArtistParams)
            emit(LoadAlbumForArtistState.Loaded(album))
            listen.emit(LoadAlbumForArtistState.Loaded(album))
        } catch (e: Exception) {
            emit(LoadAlbumForArtistState.Failure)
            listen.emit(LoadAlbumForArtistState.Failure)
        }
    }
}

sealed class LoadAlbumForArtistState {
    object Initial : LoadAlbumForArtistState()
    object Loading : LoadAlbumForArtistState()
    class Loaded(val albumWithSongs: AlbumWithSongs) : LoadAlbumForArtistState()
    object Failure : LoadAlbumForArtistState()
}
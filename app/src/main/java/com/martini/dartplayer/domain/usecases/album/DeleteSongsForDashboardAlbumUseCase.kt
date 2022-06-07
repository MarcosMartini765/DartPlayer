package com.martini.dartplayer.domain.usecases.album

import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSongsForDashboardAlbumUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    val listen =
        MutableStateFlow<DeleteSongsForDashboardAlbumState>(DeleteSongsForDashboardAlbumState.Initial)

    operator fun invoke(songs: List<Song>): Flow<DeleteSongsForDashboardAlbumState> = flow {
        try {
            emit(DeleteSongsForDashboardAlbumState.Loading)
            listen.emit(DeleteSongsForDashboardAlbumState.Loading)
            musicRepository.deleteSongsForAlbum(songs)
            emit(DeleteSongsForDashboardAlbumState.Loaded)
            listen.emit(DeleteSongsForDashboardAlbumState.Loaded)
        } catch (e: Exception) {
            emit(DeleteSongsForDashboardAlbumState.Failure)
            listen.emit(DeleteSongsForDashboardAlbumState.Failure)
        }
    }
}

sealed class DeleteSongsForDashboardAlbumState {
    object Initial : DeleteSongsForDashboardAlbumState()
    object Loading : DeleteSongsForDashboardAlbumState()
    object Loaded : DeleteSongsForDashboardAlbumState()
    object Failure : DeleteSongsForDashboardAlbumState()
}
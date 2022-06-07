package com.martini.dartplayer.presentation.components.dashboard.song

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.usecases.song.GetSongInfoState
import com.martini.dartplayer.domain.usecases.song.GetSongInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetSongInfoViewModel @Inject constructor(
    private val getSongInfoUseCase: GetSongInfoUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetSongInfoState>(GetSongInfoState.Initial)

    val state: State<GetSongInfoState> = _state

    init {
        listen()
    }

    private fun listen() {
        getSongInfoUseCase.listen
            .onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }

    fun getSongInfo(song: Song) {
        getSongInfoUseCase(song)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun getArtistInfo(artistWithAlbums: ArtistWithAlbums) {
        getSongInfoUseCase.getArtistInfo(artistWithAlbums)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun getAlbumInfo(albumWithSongs: AlbumWithSongs) {
        getSongInfoUseCase.getAlbumInfo(albumWithSongs)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun getPlaylistInfo(playlistWithSongs: PlaylistWithSongs) {
        getSongInfoUseCase.getPlaylistInfo(playlistWithSongs)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}
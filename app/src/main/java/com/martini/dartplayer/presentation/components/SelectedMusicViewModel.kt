package com.martini.dartplayer.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SelectedMusicViewModel @Inject constructor(
    private val selectedMusicUseCase: SelectedMusicUseCase
) : ViewModel() {

    private val _state = mutableStateOf(Selected(emptyList()))

    val state: State<Selected> = _state

    init {
        listen()
    }

    fun addSong(song: Song) {
        selectedMusicUseCase.addSong(song)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun addOrRemoveSong(song: Song) {
        selectedMusicUseCase.addOrRemoveSong(song)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun addOrRemoveAlbum(album: AlbumWithSongs) {
        selectedMusicUseCase.addOrRemoveAlbum(album)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun addOrRemoveArtist(artist: ArtistWithAlbums) {
        selectedMusicUseCase.addOrRemoveArtist(artist)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun addOrRemovePlaylist(playlistWithSongs: PlaylistWithSongs) {
        selectedMusicUseCase.addOrRemovePlaylist(playlistWithSongs)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun clear() {
        selectedMusicUseCase.clear()
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        selectedMusicUseCase.listen
            .onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }

}
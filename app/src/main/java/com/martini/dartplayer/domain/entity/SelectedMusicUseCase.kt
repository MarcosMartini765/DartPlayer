package com.martini.dartplayer.domain.entity

import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class SelectedMusicUseCase {

    val listen = MutableStateFlow(Selected(emptyList()))

    private val music = mutableListOf<Any>()

    fun addSong(song: Song) = flow {
        if (!music.contains(song)) {
            music.add(song)
        }
        listen.emit(Selected(music))
        emit(Selected(music))
    }

    fun addOrRemoveSong(song: Song): Flow<Selected> = flow {
        if (music.contains(song)) {
            music.remove(song)
        } else {
            music.add(song)
        }
        listen.emit(Selected(music))
        emit(Selected(music))
    }

    fun addOrRemoveAlbum(album: AlbumWithSongs): Flow<Selected> = flow {
        if (music.contains(album)) {
            music.remove(album)
        } else {
            music.add(album)
        }
        listen.emit(Selected(music))
        emit(Selected(music))
    }

    fun addOrRemoveArtist(artist: ArtistWithAlbums): Flow<Selected> = flow {
        if (music.contains(artist)) {
            music.remove(artist)
        } else {
            music.add(artist)
        }
        listen.emit(Selected(music))
        emit(Selected(music))
    }

    fun addOrRemovePlaylist(playlistWithSongs: PlaylistWithSongs) = flow {
        if (music.contains(playlistWithSongs)) {
            music.remove(playlistWithSongs)
        } else {
            music.add(playlistWithSongs)
        }
        listen.emit(Selected(music))
        emit(Selected(music))
    }

    fun removePlaylist(playlists: List<PlaylistWithSongs>) = flow {
        music.removeAll(playlists)
        listen.emit(Selected(music))
        emit(Selected(music))
    }

    fun clear(): Flow<Selected> = flow {
        music.clear()
        listen.emit(Selected(music))
        emit(Selected(music))
    }
}

class Selected(val music: List<Any>)
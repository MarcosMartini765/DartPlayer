package com.martini.dartplayer.data.local.musicManager

import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.entity.song.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveSongFromPlaylistApi @Inject constructor(
    val playerDispatcher: PlayerDispatcher
) {
    suspend operator fun invoke(songs: List<Song>) = withContext(Dispatchers.IO) {
        playerDispatcher.removeSongsFromPlaylist(songs.map { it.id })
            .launchIn(this)
    }
}
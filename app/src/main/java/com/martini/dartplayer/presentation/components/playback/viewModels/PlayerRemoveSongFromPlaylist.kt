package com.martini.dartplayer.presentation.components.playback.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.entity.song.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PlayerRemoveSongFromPlaylist @Inject constructor(
    private val playerDispatcher: PlayerDispatcher
) : ViewModel() {
    operator fun invoke(song: Song) {
        playerDispatcher.removeSongsFromPlaylist(listOf(song.id))
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
package com.martini.dartplayer.presentation.components.viewModels

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import com.martini.dartplayer.domain.usecases.PlayRequestedSongState
import com.martini.dartplayer.domain.usecases.PlayRequestedSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PlayRequestedSongViewModel @Inject constructor(
    private val playRequestedSongUseCase: PlayRequestedSongUseCase,
    private val playerDispatcher: PlayerDispatcher
) : ViewModel() {
    fun listen(): MutableSharedFlow<PlayRequestedSongState> {
        return playRequestedSongUseCase.listen
    }

    operator fun invoke(intent: Intent) {
        playRequestedSongUseCase(intent)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun playSong(song: Song) {
        playerDispatcher.startNewSession(
            PlaySongAtIndexParams(
                songs = listOf(song)
            )
        ).flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
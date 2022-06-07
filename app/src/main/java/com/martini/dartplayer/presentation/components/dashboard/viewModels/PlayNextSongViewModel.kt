package com.martini.dartplayer.presentation.components.dashboard.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.dartplayer.domain.entity.Selected
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.domain.params.player.PlayNextParams
import com.martini.dartplayer.domain.usecases.playerService.PlayNextSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PlayNextSongViewModel @Inject constructor(
    private val playNextSongsUseCase: PlayNextSongsUseCase
) : ViewModel() {
    operator fun invoke(song: Song) {
        playNextSongsUseCase(
            PlayNextParams(
                selected = Selected(listOf(song))
            )
        )
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
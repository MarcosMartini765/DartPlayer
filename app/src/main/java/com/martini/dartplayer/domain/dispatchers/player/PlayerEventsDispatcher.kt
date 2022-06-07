package com.martini.dartplayer.domain.dispatchers.player

import com.martini.dartplayer.domain.entity.player.PlaybackMode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class PlayerEventsDispatcher {

    val mediaTransitionListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)
    val isPlayingListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)
    val sessionChangedListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)
    val hasNextAndPreviousListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)
    val positionListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)
    val reorderListener = MutableSharedFlow<PlayerEvent>(0)
    val shuffleListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)
    val repeatListener = MutableStateFlow<PlayerEvent>(PlayerEvent.Initial)

    fun mediaTransition(id: Long) = flow<PlayerEvent> {
        mediaTransitionListener.emit(PlayerEvent.PlayerMediaTransition(id))
        emit(PlayerEvent.PlayerMediaTransition(id))
    }

    fun isPlaying(value: Boolean) = flow<PlayerEvent> {
        isPlayingListener.emit(PlayerEvent.PlayerIsPlaying(value))
        emit(PlayerEvent.PlayerIsPlaying(value))
    }

    fun sessionChanged(value: Boolean) = flow<PlayerEvent> {
        sessionChangedListener.emit(PlayerEvent.PlayerSessionChanged(value))
        emit(PlayerEvent.PlayerSessionChanged(value))
    }

    fun hasNextAndPreviousChanged(
        hasNext: Boolean,
        hasPrevious: Boolean
    ) = flow<PlayerEvent> {
        hasNextAndPreviousListener.emit(PlayerEvent.HasNextAndPrevious(hasNext, hasPrevious))
        emit(PlayerEvent.HasNextAndPrevious(hasNext, hasPrevious))
    }

    fun position(pos: Long) = flow<PlayerEvent> {
        positionListener.emit(PlayerEvent.Position(pos))
        emit(PlayerEvent.Position(pos))
    }

    fun reordered() = flow<PlayerEvent> {
        reorderListener.emit(PlayerEvent.Reordered)
        emit(PlayerEvent.Reordered)
    }

    fun shuffle(shuffle: Boolean) = flow<PlayerEvent> {
        shuffleListener.emit(PlayerEvent.Shuffle(shuffle))
        emit(PlayerEvent.Shuffle(shuffle))
    }

    fun repeatMode(mode: PlaybackMode) = flow {
        repeatListener.emit(PlayerEvent.RepeatMode(mode))
        emit(PlayerEvent.RepeatMode(mode))
    }
}

sealed class PlayerEvent {
    object Initial : PlayerEvent()

    class PlayerMediaTransition(
        val id: Long
    ) : PlayerEvent()

    class PlayerIsPlaying(
        val isPlaying: Boolean
    ) : PlayerEvent()

    class PlayerSessionChanged(
        val value: Boolean
    ) : PlayerEvent()

    class HasNextAndPrevious(
        val hasNext: Boolean,
        val hasPrevious: Boolean
    ) : PlayerEvent()

    class Position(
        val position: Long
    ) : PlayerEvent()

    object Reordered : PlayerEvent()

    class Shuffle(
        val shuffle: Boolean
    ) : PlayerEvent()

    class RepeatMode(
        val mode: PlaybackMode
    ) : PlayerEvent()
}
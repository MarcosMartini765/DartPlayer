package com.martini.dartplayer.domain.dispatchers.player

import com.martini.dartplayer.domain.entity.player.PlaybackMode
import com.martini.dartplayer.domain.params.dashboard.PlayNextSongsParams
import com.martini.dartplayer.domain.params.dashboard.PlaySongAtIndexParams
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class PlayerDispatcher {
    val listen = MutableSharedFlow<PlayerAction>(0)

    fun startNewSession(params: PlaySongAtIndexParams) = flow<PlayerAction> {
        listen.emit(PlayerAction.StartNewSession(params))
        emit(PlayerAction.StartNewSession(params))
    }

    fun pauseOrPlay() = flow<PlayerAction> {
        listen.emit(PlayerAction.PlayerPlayOrPause)
        emit(PlayerAction.PlayerPlayOrPause)
    }

    fun next() = flow<PlayerAction> {
        listen.emit(PlayerAction.PlayerNext)
        emit(PlayerAction.PlayerNext)
    }

    fun back() = flow<PlayerAction> {
        listen.emit(PlayerAction.PlayerBack)
        emit(PlayerAction.PlayerBack)
    }

    fun playNextSongs(params: PlayNextSongsParams) = flow<PlayerAction> {
        listen.emit(PlayerAction.PlayNextSongs(params))
        emit(PlayerAction.PlayNextSongs(params))
    }

    fun removeSongsFromPlaylist(ids: List<Long>) = flow<PlayerAction> {
        listen.emit(PlayerAction.PlayerRemoveSongsFromPlaylist(ids))
        emit(PlayerAction.PlayerRemoveSongsFromPlaylist(ids))
    }

    fun seekTo(position: Float) = flow<PlayerAction> {
        listen.emit(PlayerAction.SeekTo(position))
        emit(PlayerAction.SeekTo(position))
    }

    fun jumpTo(index: Int) = flow<PlayerAction> {
        listen.emit(PlayerAction.JumpTo(index))
        emit(PlayerAction.JumpTo(index))
    }

    fun reorder(oldIndex: Int, newIndex: Int) = flow<PlayerAction> {
        listen.emit(PlayerAction.Reorder(oldIndex, newIndex))
        emit(PlayerAction.Reorder(oldIndex, newIndex))
    }

    fun shuffle(shuffle: Boolean) = flow<PlayerAction> {
        listen.emit(PlayerAction.Shuffle(shuffle))
        emit(PlayerAction.Shuffle(shuffle))
    }

    fun repeatMode(mode: PlaybackMode) = flow<PlayerAction> {
        listen.emit(PlayerAction.RepeatMode(mode))
        emit(PlayerAction.RepeatMode(mode))
    }
}

sealed class PlayerAction {

    class StartNewSession(
        val params: PlaySongAtIndexParams
    ) : PlayerAction()

    class PlayNextSongs(
        val params: PlayNextSongsParams
    ) : PlayerAction()

    object PlayerPlayOrPause : PlayerAction()

    object PlayerNext : PlayerAction()

    object PlayerBack : PlayerAction()

    class PlayerRemoveSongsFromPlaylist(
        val ids: List<Long>
    ) : PlayerAction()

    class SeekTo(
        val position: Float
    ) : PlayerAction()

    class JumpTo(
        val index: Int
    ) : PlayerAction()

    class Reorder(
        val oldIndex: Int,
        val newIndex: Int
    ) : PlayerAction()

    class Shuffle(
        val shuffle: Boolean
    ) : PlayerAction()

    class RepeatMode(
        val mode: PlaybackMode
    ) : PlayerAction()
}

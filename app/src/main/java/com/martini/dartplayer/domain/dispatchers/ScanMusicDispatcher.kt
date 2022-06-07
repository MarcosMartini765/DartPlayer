package com.martini.dartplayer.domain.dispatchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class ScanMusicDispatcher {
    val listen = MutableSharedFlow<ScanMusicAction>(0)

    fun scan(): Flow<ScanMusicAction> = flow<ScanMusicAction> {
        listen.emit(ScanMusicAction.Scan)
        emit(ScanMusicAction.Scan)
    }
}

sealed class ScanMusicAction {
    object Initial : ScanMusicAction()
    object Scan : ScanMusicAction()
}
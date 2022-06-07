package com.martini.dartplayer.domain.usecases

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class ExitAppUseCase {

    val listen = MutableSharedFlow<ExitAppAction>(0)

    operator fun invoke() = flow<ExitAppAction> {
        emit(ExitAppAction.Exit)
        listen.emit(ExitAppAction.Exit)
    }
}

sealed class ExitAppAction {
    object Exit: ExitAppAction()
}
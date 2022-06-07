package com.martini.dartplayer.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class SearchUseCase {

    val listen = MutableStateFlow(false)

    operator fun invoke(value: Boolean): Flow<Boolean> = flow {
        emit(value)
        listen.emit(value)
    }
}
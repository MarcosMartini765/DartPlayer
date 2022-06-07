package com.martini.dartplayer.domain.usecases.song

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSongSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {

    val listen = MutableStateFlow<GetSongSortOrderState>(GetSongSortOrderState.Loading)

    operator fun invoke() = flow {
        try {
            emit(GetSongSortOrderState.Loading)
            listen.emit(GetSongSortOrderState.Loading)
            val order = sortOrderRepository.getSongSortOrder()
            emit(GetSongSortOrderState.Loaded(order))
            listen.emit(GetSongSortOrderState.Loaded(order))
        } catch (e: Exception) {
            emit(GetSongSortOrderState.Failure)
            listen.emit(GetSongSortOrderState.Failure)
        }
    }
}

sealed class GetSongSortOrderState {
    object Loading: GetSongSortOrderState()
    class Loaded(
        val order: SortOrder
    ): GetSongSortOrderState()
    object Failure: GetSongSortOrderState()
}
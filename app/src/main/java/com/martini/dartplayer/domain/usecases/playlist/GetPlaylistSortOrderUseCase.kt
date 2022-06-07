package com.martini.dartplayer.domain.usecases.playlist

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlaylistSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke() = flow {
        try {
            emit(GetPlaylistSortOrderState.Loading)
            val order = sortOrderRepository.getPlaylistSortOrder()
            emit(GetPlaylistSortOrderState.Loaded(order))
        } catch (e: Exception) {
            emit(GetPlaylistSortOrderState.Failure)
        }
    }
}

sealed class GetPlaylistSortOrderState {
    object Loading: GetPlaylistSortOrderState()
    class Loaded(
        val order: SortOrder
    ): GetPlaylistSortOrderState()
    object Failure: GetPlaylistSortOrderState()
}
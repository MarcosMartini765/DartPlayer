package com.martini.dartplayer.domain.usecases.album

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAlbumSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke() = flow {
        try {
            emit(GetAlbumSortOrderState.Loading)
            val order = sortOrderRepository.getAlbumSortOrder()
            emit(GetAlbumSortOrderState.Loaded(order))
        } catch (e: Exception) {
            emit(GetAlbumSortOrderState.Failure)
        }
    }
}

sealed class GetAlbumSortOrderState {
    object Loading: GetAlbumSortOrderState()
    class Loaded(
        val order: SortOrder
    ): GetAlbumSortOrderState()
    object Failure: GetAlbumSortOrderState()
}
package com.martini.dartplayer.domain.usecases.artist

import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetArtistSortOrderUseCase @Inject constructor(
    private val sortOrderRepository: SortOrderRepository
) {
    operator fun invoke() = flow {
        try {
            emit(GetArtistSortOrderState.Loading)
            val order = sortOrderRepository.getArtistSortOrder()
            emit(GetArtistSortOrderState.Loaded(order))
        } catch (e: Exception) {
            emit(GetArtistSortOrderState.Failure)
        }
    }
}

sealed class GetArtistSortOrderState {
    object Loading: GetArtistSortOrderState()
    class Loaded(
        val order: SortOrder
    ) : GetArtistSortOrderState()
    object Failure: GetArtistSortOrderState()
}
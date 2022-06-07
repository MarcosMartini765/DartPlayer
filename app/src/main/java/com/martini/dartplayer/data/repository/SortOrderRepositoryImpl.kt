package com.martini.dartplayer.data.repository

import com.martini.dartplayer.data.local.SortOrderApi
import com.martini.dartplayer.domain.entity.song.SortOrder
import com.martini.dartplayer.domain.repository.SortOrderRepository
import javax.inject.Inject

class SortOrderRepositoryImpl @Inject constructor(
    private val sortOrderApi: SortOrderApi
) : SortOrderRepository {
    override fun getSongSortOrder(): SortOrder {
        return sortOrderApi.getSongSortOrder()
    }

    override fun getAlbumSortOrder(): SortOrder {
        return sortOrderApi.getAlbumSortOrder()
    }

    override fun getArtistSortOrder(): SortOrder {
        return sortOrderApi.getArtistSortOrder()
    }

    override fun getPlaylistSortOrder(): SortOrder {
        return sortOrderApi.getPlaylistSortOrder()
    }

    override fun setSongSortOrder(order: SortOrder) {
        return sortOrderApi.setSongSortOrder(order)
    }

    override fun setAlbumSortOrder(order: SortOrder) {
        return sortOrderApi.setAlbumSortOrder(order)
    }

    override fun setArtistSortOrder(order: SortOrder) {
        return sortOrderApi.setArtistSortOrder(order)
    }

    override fun setPlaylistSortOrder(order: SortOrder) {
        return sortOrderApi.setPlaylistSortOrder(order)
    }
}
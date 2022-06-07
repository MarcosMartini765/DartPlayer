package com.martini.dartplayer.data.local

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Timeline
import com.martini.dartplayer.domain.entity.cachedSongs.CachedPlaybackParameters
import com.martini.dartplayer.domain.entity.cachedSongs.CachedSongsId
import com.martini.dartplayer.domain.entity.daos.CachedSongsDao
import com.martini.dartplayer.domain.params.player.GetCachedPlaylistParams
import com.martini.dartplayer.domain.params.player.SetCachedPlaylistParams
import kotlinx.coroutines.yield
import javax.inject.Inject

class CachedPlaylistApi @Inject constructor(
    private val cachedSongsDao: CachedSongsDao,
) {
    suspend fun clear() {
        cachedSongsDao.clear()
    }

    suspend fun insertPlaylist(params: SetCachedPlaylistParams) {
        cachedSongsDao.clear()

        val timeline = params.timeline

        val ids = mutableListOf<CachedSongsId>()
        val items = mutableMapOf<Int, MediaItem>()
        val defaultWindow = Timeline.Window()
        var index = timeline.getFirstWindowIndex(params.shuffled)
        val foundIndexes = mutableListOf<Int>()

        while (index != C.INDEX_UNSET) {
            yield()
            if (foundIndexes.contains(index)) break
            foundIndexes.add(index)
            val window = timeline.getWindow(index, defaultWindow)
            items.putIfAbsent(index, window.mediaItem)
            index = timeline.getNextWindowIndex(
                index,
                params.repeat,
                params.shuffled
            )
        }

        index = 0
        items.forEach { (idx, item) ->
            item.mediaId.toLongOrNull()?.let {
                ids.add(
                    CachedSongsId(
                        id = it,
                        songIndex = idx,
                        queryIndex = index
                    )
                )
                ++index
            }
        }

        cachedSongsDao.insertAll(ids)
    }

    suspend fun updatePlaybackParameters(params: CachedPlaybackParameters) {
        cachedSongsDao.updateParameters(params)
    }

    suspend fun getParams(): CachedPlaybackParameters {
        return cachedSongsDao.getParams()
    }

    suspend fun getPlaylist(): GetCachedPlaylistParams {

        val songsDb = cachedSongsDao.getAllSongs().associateBy { it.id }

        val ids = cachedSongsDao.getPlaylist()

        return GetCachedPlaylistParams(
            songs = ids.mapNotNull { songsDb[it.id] }
        )
    }
}
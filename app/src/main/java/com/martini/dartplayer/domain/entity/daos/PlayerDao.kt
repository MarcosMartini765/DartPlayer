package com.martini.dartplayer.domain.entity.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martini.dartplayer.domain.entity.player.PlayerSettings

@Dao
interface PlayerDao {
    @Query("SELECT * FROM playersettings WHERE id == 1")
    suspend fun getSettings(): PlayerSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: PlayerSettings)
}
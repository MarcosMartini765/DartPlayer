package com.martini.dartplayer.domain.entity.files

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalFilesDao {
    @Query("SELECT * FROM localfilessettings WHERE id == 1")
    suspend fun getSettings(): LocalFilesSettings

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeSettings(settings: LocalFilesSettings)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: LocalFilesSettings)
}
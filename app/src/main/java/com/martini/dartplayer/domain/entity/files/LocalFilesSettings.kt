package com.martini.dartplayer.domain.entity.files

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.martini.dartplayer.presentation.components.settings.local.Frequency

@Entity
data class LocalFilesSettings(
    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(defaultValue = "NEVER")
    val frequency: Frequency
)

fun LocalFilesSettings.copy(
    id: Long = this.id,
    frequency: Frequency = this.frequency
): LocalFilesSettings {
    return LocalFilesSettings(
        id,
        frequency
    )
}
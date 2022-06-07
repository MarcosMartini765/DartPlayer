package com.martini.dartplayer.services.playerService

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.martini.dartplayer.MainActivity
import java.io.File

class PlayerMediaDescriptionAdapter(
    private val context: Context
) : PlayerNotificationManager.MediaDescriptionAdapter {

    private fun createPendingIntent(): PendingIntent? {
        return Intent(context, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }
    }

    override fun getCurrentContentTitle(player: Player): CharSequence {
        val item = player.currentMediaItem

        val title = item?.let {
            val metadata = it.mediaMetadata
            metadata.title
        } ?: "Unknown"

        return title
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return createPendingIntent()
    }

    override fun getCurrentContentText(player: Player): CharSequence {
        val item = player.currentMediaItem

        val artist = item?.let {
            val metadata = it.mediaMetadata
            metadata.artist
        } ?: "Unknown"

        return artist
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val item = player.currentMediaItem

        val thumbnail = item?.let { media ->
            val metadata = media.mediaMetadata
            val uri = metadata.artworkUri
            uri?.let {
                val file = File(context.filesDir, media.mediaId)
                BitmapFactory.decodeFile(file.path)
            }
        }

        return thumbnail
    }
}
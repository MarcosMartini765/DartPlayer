package com.martini.dartplayer.data.local.musicManager

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MusicThumbnailApi @Inject constructor(
    private val context: Context
) {
    operator fun invoke(uri: Uri, id: Long): String? {
        return try {
            val file = File(context.filesDir, id.toString())
            val thumbnail: Bitmap = context.contentResolver
                .loadThumbnail(uri, Size(640, 480), null)
            val outputStream = FileOutputStream(file)
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 20, outputStream)
            outputStream.flush()
            outputStream.close()

            file.absolutePath
        } catch (e: Exception) {
            null
        }
    }
}
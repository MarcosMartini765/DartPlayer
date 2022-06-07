package com.martini.dartplayer.data.local.musicManager

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.martini.dartplayer.R
import com.martini.dartplayer.data.model.SongResource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MusicGetterApi @Inject constructor(
    private val context: Context,
    private val musicThumbnailApi: MusicThumbnailApi,
    private val isProbablyAudio: IsProbablyAudio
) {

    private val unknown = context.getString(R.string.unknown)

    operator fun invoke(): MutableList<SongResource> {

        val songs = mutableListOf<SongResource>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ARTIST,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.GENRE,
            MediaStore.Audio.Media.IS_TRASHED,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.RELATIVE_PATH,
            MediaStore.Audio.Media.DATE_ADDED
        )
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS).toString()
        )
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val query = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val albumArtistColumn =
                cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val genreColumn = cursor.getColumnIndex(MediaStore.Audio.Media.GENRE)
            val isTrashedColumn = cursor.getColumnIndex(MediaStore.Audio.Media.IS_TRASHED)
            val isMusicColumn = cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)
            val dateColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)
            val displayNameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
            val dateAddedColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name =
                    if (nameColumn != -1) cursor.getString(nameColumn)
                        ?: if (displayNameColumn != -1) cursor.getString(displayNameColumn) else unknown else unknown
                val album =
                    if (albumColumn != -1) cursor.getString(albumColumn) ?: unknown else unknown
                val albumArtist =
                    if (albumArtistColumn != -1) cursor.getString(albumArtistColumn)
                        ?: unknown else unknown
                val artist =
                    if (artistColumn != -1) cursor.getString(artistColumn) ?: unknown else unknown
                val duration = if (durationColumn != -1) cursor.getLong(durationColumn) else 0
                val genre =
                    if (genreColumn != -1) cursor.getString(genreColumn) ?: unknown else unknown
                val isTrashed = if (isTrashedColumn != -1) cursor.getInt(isTrashedColumn) else 0
                val isMusic = if (isMusicColumn != -1) cursor.getInt(isMusicColumn) else 1
                val date = if (dateColumn != -1) cursor.getLong(dateColumn) else 0
                val displayName = cursor.getString(displayNameColumn)
                val dateAdded = if (dateAddedColumn != -1) cursor.getLong(dateAddedColumn) else 0

                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                if (isMusic != 0 && isTrashed == 0 && isProbablyAudio(displayName)) {
                    songs.add(
                        SongResource(
                            id = id,
                            name = name,
                            artist = artist,
                            albumArtist = albumArtist,
                            album = album,
                            genre = genre,
                            duration = duration,
                            uri = uri,
                            imageUri = musicThumbnailApi(uri, id),
                            dateModified = date,
                            dateAdded = dateAdded
                        )
                    )
                }
            }
        }

        return songs
    }

    fun getForId(songId: Long): SongResource {
        val songs = mutableListOf<SongResource>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ARTIST,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.GENRE,
            MediaStore.Audio.Media.IS_TRASHED,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.RELATIVE_PATH,
            MediaStore.Audio.Media.DATE_ADDED
        )
        val selection = "${MediaStore.Audio.Media._ID} == ?"
        val selectionArgs = arrayOf(
            songId.toString()
        )
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val query = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            cursor.moveToFirst()
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val albumArtistColumn =
                cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val genreColumn = cursor.getColumnIndex(MediaStore.Audio.Media.GENRE)
            val isTrashedColumn = cursor.getColumnIndex(MediaStore.Audio.Media.IS_TRASHED)
            val isMusicColumn = cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)
            val dateColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)
            val displayNameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
            val dateAddedColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)

            val id = cursor.getLong(idColumn)
            val name =
                if (nameColumn != -1) cursor.getString(nameColumn)
                    ?: if (displayNameColumn != -1) cursor.getString(displayNameColumn) else unknown else unknown
            val album =
                if (albumColumn != -1) cursor.getString(albumColumn) ?: unknown else unknown
            val albumArtist =
                if (albumArtistColumn != -1) cursor.getString(albumArtistColumn)
                    ?: unknown else unknown
            val artist =
                if (artistColumn != -1) cursor.getString(artistColumn) ?: unknown else unknown
            val duration = if (durationColumn != -1) cursor.getLong(durationColumn) else 0
            val genre =
                if (genreColumn != -1) cursor.getString(genreColumn) ?: unknown else unknown
            val isTrashed = if (isTrashedColumn != -1) cursor.getInt(isTrashedColumn) else 0
            val isMusic = if (isMusicColumn != -1) cursor.getInt(isMusicColumn) else 1
            val date = if (dateColumn != -1) cursor.getLong(dateColumn) else 0
            val displayName = cursor.getString(displayNameColumn)
            val dateAdded = if (dateAddedColumn != -1) cursor.getLong(dateAddedColumn) else 0

            val uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            )

            if (isMusic != 0 && isTrashed == 0 && isProbablyAudio(displayName)) {
                songs.add(
                    SongResource(
                        id = id,
                        name = name,
                        artist = artist,
                        albumArtist = albumArtist,
                        album = album,
                        genre = genre,
                        duration = duration,
                        uri = uri,
                        imageUri = musicThumbnailApi(uri, id),
                        dateModified = date,
                        dateAdded = dateAdded
                    )
                )
            }
        }
        return songs.first()
    }
}
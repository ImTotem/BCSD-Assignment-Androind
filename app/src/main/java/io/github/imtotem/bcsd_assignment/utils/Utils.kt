package io.github.imtotem.bcsd_assignment.utils

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import java.text.SimpleDateFormat
import java.util.*

fun setDurationFormat(duration: Long): String =
    SimpleDateFormat("mm:ss", Locale.getDefault()).format(duration)

fun getAlbumUri(id: Long): Uri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, id)
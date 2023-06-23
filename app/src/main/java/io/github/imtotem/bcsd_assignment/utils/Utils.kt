package io.github.imtotem.bcsd_assignment.utils

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

fun setDurationFormat(duration: Long): String =
    SimpleDateFormat("mm:ss", Locale.getDefault()).format(Date(duration))

fun getAlbumUri(id: Long): Uri = Uri.withAppendedPath(
    Uri.parse("content://media/external/audio/albumart"),
    id.toString()
)
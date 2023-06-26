package io.github.imtotem.bcsd_assignment.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicItem(
    val id: Long,
    val title: String,
    val artist: String,
    val albumId: Long,
    val duration: String
) : Parcelable
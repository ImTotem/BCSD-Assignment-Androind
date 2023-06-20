package io.github.imtotem.bcsd_assignment.item

data class Music(
    val id: Long,
    val title: String,
    val artist: String,
    val albumId: Long,
    val duration: Long
)
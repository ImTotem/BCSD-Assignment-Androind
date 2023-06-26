package io.github.imtotem.bcsd_assignment.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class CustomMediaPlayer: MediaPlayer() {
    private var dataSource: Uri? = null

    override fun setDataSource(context: Context, uri: Uri) {
        super.setDataSource(context, uri)
        dataSource = uri
    }

    fun getDataSource(): Uri? = dataSource
}
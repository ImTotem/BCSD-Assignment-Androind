package io.github.imtotem.bcsd_assignment.service

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import androidx.annotation.RequiresApi
import io.github.imtotem.bcsd_assignment.item.MusicItem
import io.github.imtotem.bcsd_assignment.notification.MediaNotification
import io.github.imtotem.bcsd_assignment.player.CustomMediaPlayer
import io.github.imtotem.bcsd_assignment.receiver.Receiver
import io.github.imtotem.bcsd_assignment.utils.Constants

class MainService : Service() {
    private var mediaPlayer: CustomMediaPlayer? = null
    private lateinit var notification: MediaNotification
    private val receiver = Receiver()

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        notification = MediaNotification(baseContext)
        with(notification) {
            createNotificationChannel()

            startForeground(Constants.NOTIFICATION_ID, getBuilder().build())
        }

        initReceiver()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Constants.MUSIC_RESUME -> {
                intent.getParcelableExtra("musicItem", MusicItem::class.java)?.let { resume(it) }
            }
            Constants.MUSIC_STOP -> {
                stop()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initReceiver() {
        val filter = IntentFilter(Intent.ACTION_BATTERY_LOW)
        registerReceiver(receiver, filter)
    }

    private fun setDataSource(uri: Uri) {
        mediaPlayer = CustomMediaPlayer()
        mediaPlayer?.setDataSource(baseContext, uri)
        mediaPlayer?.prepare()
    }

    private fun resume(musicItem: MusicItem) {
        val uri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, musicItem.id)

        val prevUri = mediaPlayer?.getDataSource()

        if (prevUri != uri) {
            if (prevUri != null) stop()
            setDataSource(uri)
        }

        var isPlaying = false
        when (mediaPlayer?.isPlaying) {
            true -> mediaPlayer?.pause()
            else -> {
                mediaPlayer?.start()
                isPlaying = true
            }
        }

        notification.update(musicItem, isPlaying)
    }

    private fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopSelf()
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
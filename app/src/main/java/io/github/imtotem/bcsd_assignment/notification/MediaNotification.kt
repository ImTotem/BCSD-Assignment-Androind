package io.github.imtotem.bcsd_assignment.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.core.app.NotificationManagerCompat
import io.github.imtotem.bcsd_assignment.MainActivity
import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.item.MusicItem
import io.github.imtotem.bcsd_assignment.service.MainService
import io.github.imtotem.bcsd_assignment.utils.Constants

class MediaNotification(private val baseContext: Context) {

    private val playIcon = Icon.createWithResource(baseContext, R.drawable.baseline_play_arrow_24)
    private val pauseIcon = Icon.createWithResource(baseContext, R.drawable.baseline_pause_24)
    private val stopIcon = Icon.createWithResource(baseContext, R.drawable.baseline_stop_24)

    private val containerPendingIntent = PendingIntent.getActivity(
        baseContext,
        0,
        Intent(baseContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        },
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    private val stopAction = Notification.Action.Builder(
        stopIcon,
        "Stop",
        getPendingIntent(Constants.MUSIC_STOP, null)
    ).build()

    private val builder = Notification.Builder(baseContext, Constants.CHANNEL_ID)
        .setStyle(
            Notification.MediaStyle().setShowActionsInCompactView(0, 1)
        )
        .setVisibility(Notification.VISIBILITY_PUBLIC)
        .setSmallIcon(Icon.createWithResource(baseContext, R.drawable.baseline_music_note_24))
        .addAction(
            Notification.Action.Builder(
                playIcon,
                "Play",
                getPendingIntent(Constants.MUSIC_RESUME, null)
            ).build()
        )
        .addAction(stopAction)
        .setContentIntent(containerPendingIntent)
        .setContentTitle("타이틀")
        .setContentText("아티스트")
        .setOngoing(true)

    fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = Constants.CHANNEL_DESC
            }

        val notificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun getBuilder(): Notification.Builder = builder

    fun update(musicItem: MusicItem, isPlaying: Boolean) {
        var actionIcon: Icon = playIcon
        var actionTitle = "Play"
        if (isPlaying) {
            actionIcon = pauseIcon
            actionTitle = "Pause"
        }

        builder.setActions(
            Notification.Action.Builder(
                actionIcon,
                actionTitle,
                getPendingIntent(Constants.MUSIC_RESUME, musicItem)
            ).build(),
            stopAction
        )
        .setContentTitle(musicItem.title)
        .setContentText(musicItem.artist)

        if ( MainActivity.permission.checkPermission(Manifest.permission.POST_NOTIFICATIONS) )
            NotificationManagerCompat.from(baseContext)
                .notify(Constants.NOTIFICATION_ID, builder.build())
    }

    private fun getPendingIntent(action: String, musicItem: MusicItem?): PendingIntent =
        PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MainService::class.java).apply {
                this.action = action
                if ( musicItem != null ) putExtra("musicItem", musicItem)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
}
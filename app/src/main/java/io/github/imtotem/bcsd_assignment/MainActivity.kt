package io.github.imtotem.bcsd_assignment

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var builder: NotificationCompat.Builder
    private var count = 0

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {

        binding.countTextview.text = count.toString()

        createNotificationChannel()
        initBuilder()
    }

    override fun initEvent() {

        with(binding) {
            /** 토스트 버튼 클릭 */
            buttonToast.setOnClickListener {
                Toast.makeText(this@MainActivity, "Toast 먹고싶다", Toast.LENGTH_SHORT).show()
            }

            /** 카운트 버튼 클릭 */
            buttonCount.setOnClickListener {
                count++
                countTextview.text = count.toString()
            }

            /** 랜덤 버튼 클릭 */
            buttonRandom.setOnClickListener {
                builder.setContentIntent(createPendingIntent())
                notifyNotification()
            }
        }
    }

    /** Notification Channel 생성 */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val name = CHANNEL_ID
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /** Notification builder 생성 */
    private fun initBuilder() {
        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
    }

    /** PendingIntent 생성 */
    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, RandomActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("count", count)
        }

        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    /** Notification 보내기 */
    private fun notifyNotification() {
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERM_REQUEST_CODE
                    )
                    return
                }
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    /** 권한 요청 거절했을 때 */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            NOTIFICATION_PERM_REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) notifyNotification()
        }
    }

    companion object {
        const val CHANNEL_ID = "Random"
        const val CHANNEL_DESCRIPTION = "알림을 클릭하면 random activity가 열림"
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_PERM_REQUEST_CODE = 123
    }
}
package io.github.imtotem.bcsd_assignment.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Receiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context, "Low Battery", Toast.LENGTH_SHORT).show()
        }
    }
}
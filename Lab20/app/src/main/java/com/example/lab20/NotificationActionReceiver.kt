package com.example.lab20



import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: ""
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when(action) {
            "RESET_COLOR" -> {
                val resetIntent = Intent("RESET_COLOR_ACTION")
                LocalBroadcastManager.getInstance(context).sendBroadcast(resetIntent)
                notificationManager.cancel(1)
            }
            "COLOR_INPUT" -> {
                val text =
                    RemoteInput.getResultsFromIntent(intent!!)?.getCharSequence("COLOR_INPUT_REPLY").toString()
                val selectIntent = Intent("SET_COLOR_ACTION").apply {
                    putExtra("SELECT_COLOR", text)
                }

                LocalBroadcastManager.getInstance(context).sendBroadcast(selectIntent)
                notificationManager.cancel(1)
            }

        }

    }
}
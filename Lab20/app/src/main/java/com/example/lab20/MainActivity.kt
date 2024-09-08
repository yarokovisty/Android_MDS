package com.example.lab20

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lab20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager
    private val colorReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Сбрасываем цвет на белый
            when (intent.action) {
                "RESET_COLOR_ACTION" -> {
                    window.decorView.setBackgroundColor(Color.WHITE)
                    binding.textView.text = "Выбран цвет: FFFFFF"
                }
                "SET_COLOR_ACTION" -> {
                    val colorRGB = intent.getStringExtra("SELECT_COLOR")
                    val color = Color.parseColor("#$colorRGB")
                    window.decorView.setBackgroundColor(color)
                    binding.textView.text = "Выбран цвет: $colorRGB"
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        register()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        requestPermission()
        createNotificationChannel()

        binding.btnNotification.setOnClickListener {
            showNotification()
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(colorReceiver)
        super.onDestroy()
    }


    private fun register() {
        val intentFilter = IntentFilter().apply {
            addAction("RESET_COLOR_ACTION")
            addAction("SET_COLOR_ACTION")
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(colorReceiver, intentFilter)

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_ACCESS_NOTIFICATION
            )
        }

    }

    private fun checkPermission(): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_MSG_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_MSG_ID)
            .setSmallIcon(R.drawable.icon_paint)
            .setContentTitle("Управляющий")
            .setContentText("Отсюда можно управлять цвтом фона программы")
            .setContentIntent(createTouchNotification())
            .setAutoCancel(true)
            .addAction(R.drawable.icon_paint, "Сбросить цвет", createResetBtnNotification())
            .addAction(createSelectColorBtnNotification())
        val notification = builder.build()

        notificationManager.notify(NOTIFICATION_ID, notification)

    }

    private fun createTouchNotification(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createResetBtnNotification(): PendingIntent {
        val resetIntent = Intent(this, NotificationActionReceiver::class.java).apply {
            action = "RESET_COLOR"
        }
        return PendingIntent.getBroadcast(this, 0, resetIntent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createSelectColorBtnNotification(): NotificationCompat.Action {
        val intent = Intent(this, NotificationActionReceiver::class.java).apply {
            action = "COLOR_INPUT"
        }

        val remoteInput = RemoteInput.Builder("COLOR_INPUT_REPLY").run {
            setLabel("Введите цвет в формате RRGGBB:")
            build()
        }

        val replyPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val setColorAction = NotificationCompat.Action.Builder(R.drawable.icon_paint, "Выбор цвета", replyPendingIntent)
            .addRemoteInput(remoteInput)
            .build()


        return setColorAction
    }


    private fun showNotification() {
        if (checkPermission()) {
            createNotification()
        } else {
            requestPermission()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_NOTIFICATION = 11
        private const val CHANNEL_NAME = "Цвет"
        private const val CHANNEL_MSG_ID = "lab20_channel_msg"
        private const val NOTIFICATION_ID = 1

    }
}
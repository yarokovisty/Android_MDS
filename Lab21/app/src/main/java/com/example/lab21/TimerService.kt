package com.example.lab21

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class TimerService : Service() {

    private val notificationId = 1
    private val channelId = "timer_channel"

    private lateinit var handler: Handler
    private lateinit var looper: Looper

    private var timeRemainingMillis: Long = 0
    private var timerRunning = false

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val handlerThread = HandlerThread("TimerThread")
        handlerThread.start()
        looper = handlerThread.looper
        handler = Handler(looper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val minutes = intent?.getIntExtra("minutes", 0) ?: 0
        val seconds = intent?.getIntExtra("seconds", 0) ?: 0
        timeRemainingMillis = (minutes * 60 + seconds) * 1000L

        startTimer()

        return START_NOT_STICKY
    }

    private fun startTimer() {
        if (timerRunning) return
        timerRunning = true

        handler.post(object : Runnable {
            override fun run() {
                if (timeRemainingMillis <= 0) {
                    stopSelf()
                    val finishIntent = Intent("TIMER_FINISH")
                    sendBroadcast(finishIntent)
                } else {
                    val minutesLeft = timeRemainingMillis / 60000
                    val secondsLeft = (timeRemainingMillis % 60000) / 1000
                    val timeLeft = String.format("%02d:%02d", minutesLeft, secondsLeft)


                    val notification = createNotification(timeLeft)
                    startForeground(notificationId, notification)
                    

                    Log.i("MyLog", timeLeft)

                    val updateIntent = Intent("TIMER_UPDATE")
                    updateIntent.putExtra("time_left", timeLeft)
                    sendBroadcast(updateIntent)

                    timeRemainingMillis -= 1000
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        looper.quitSafely()
        timerRunning = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelId, "Timer Channel", NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(timeLeft: String): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Timer")
            .setContentText("Time remaining: $timeLeft")
            .setSmallIcon(R.drawable.icon_timer)
            .build()
    }
}
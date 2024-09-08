package com.example.lab21

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab21.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private val timerUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = with(binding) {
            val timeLeft = intent?.getStringExtra("time_left") ?: DEFAULT_TIMER
            tvTimer.text = timeLeft

            if (timeLeft != DEFAULT_TIMER) {
                btnLaunch.text = getString(R.string.stop)
                etMinutes.isEnabled = false
                etSeconds.isEnabled = false
                isRunning = true
            }

        }
    }
    private val timerFinishReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = with(binding) {
            tvTimer.text = getString(R.string.timer)
            btnLaunch.text = getString(R.string.start)
            etMinutes.isEnabled = true
            etSeconds.isEnabled = true
            isRunning = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLaunch.setOnClickListener {
            if (isRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }



        if (!checkPermission()) requestPermission()
    }

    @SuppressLint("DefaultLocale")
    private fun startTimer() = with(binding) {
        val minutes = etMinutes.text.toString().toIntOrNull() ?: 0
        val seconds = etSeconds.text.toString().toIntOrNull() ?: 0

        val intent = Intent(this@MainActivity, TimerService::class.java).apply {
            putExtra("minutes", minutes)
            putExtra("seconds", seconds)
        }
        startService(intent)

        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
        btnLaunch.text = getString(R.string.stop)
        etMinutes.isEnabled = false
        etSeconds.isEnabled = false
        isRunning = true
    }

    private fun stopTimer() = with(binding) {
        stopService(Intent(this@MainActivity, TimerService::class.java))
        tvTimer.text = getString(R.string.timer)
        btnLaunch.text = getString(R.string.start)
        etMinutes.isEnabled = true
        etSeconds.isEnabled = true
        isRunning = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        registerReceiver(timerUpdateReceiver, IntentFilter("TIMER_UPDATE"), RECEIVER_EXPORTED)
        registerReceiver(timerFinishReceiver, IntentFilter("TIMER_FINISH"), RECEIVER_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(timerUpdateReceiver)
        unregisterReceiver(timerFinishReceiver)
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


    companion object {
        private const val PERMISSION_REQUEST_ACCESS_NOTIFICATION = 11
        private const val DEFAULT_TIMER = "00:00"

    }
}
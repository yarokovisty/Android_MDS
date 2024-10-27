package com.example.lab27

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.util.Pair
import android.view.SurfaceHolder
import android.view.SurfaceView

class BallSurface(context: Context, private val sensorManager: SensorManager) : SurfaceView(context),
    SurfaceHolder.Callback {

    private var thread: BallThread? = null
    private var ball = Ball()

    private var listener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_GRAVITY) {
                acceleration = Pair(event.values[0], event.values[1])
            }
        }
    }
    private var acceleration = Pair(0f, 0f)

    init {
        holder.addCallback(this)
        thread = BallThread(holder, this)
        isFocusable = true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        thread?.setRunning(true)
        thread?.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread?.setRunning(false)
                thread?.join()
            } catch (e: Exception) {
                Log.d("Test", e.toString())
            }
            retry = false
        }
        thread = null
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE)

        ball.draw(canvas)
    }

    fun update() {
        ball.update(acceleration)
    }

}
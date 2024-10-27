package com.example.lab27

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point

class Ball {

    private var movingVectorX: Int = 0
    private var movingVectorY: Int = 0
    private var lastDrawNanoTime: Long = -1
    private val radius = 50f

    private var currentPoint = Point(0, 0)

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        currentPoint.x = screenWidth / 2
        currentPoint.y = screenHeight / 2
    }

    fun draw(canvas: Canvas) {

        canvas.drawCircle(
            currentPoint.x.toFloat(),
            currentPoint.y.toFloat(),
            radius,
            Paint().apply { color = Color.BLACK })

        lastDrawNanoTime = System.nanoTime()
    }

    fun update(acceleration: android.util.Pair<Float, Float>) {

        val now = System.nanoTime()

        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        movingVectorX -= (acceleration.first).toInt()
        movingVectorY += (acceleration.second).toInt()


        if (currentPoint.x >= screenWidth - radius || currentPoint.x <= radius) {
            movingVectorX = (movingVectorX.toFloat() * (-0.9f)).toInt()
        }

        if (currentPoint.y >= screenHeight - radius || currentPoint.y <= radius) {
            movingVectorY = (movingVectorY.toFloat() * (-0.9f)).toInt()
        }

        currentPoint.x += movingVectorX
        currentPoint.y += movingVectorY
    }

}
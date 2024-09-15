package com.example.lab24

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

class Snowflake(private val containerWidth: Int,
                private val containerHeight: Int,
                private val maxSize: Float,
                private val maxSpeed: Float) {

    private var size = 0.0f
    private var speed = 0.0f
    private var x = 0.0f
    private var y = 0.0f
    private var p = Paint().apply {
        color = Color.WHITE
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    init {
        reset()
    }

    private fun reset() {
        size = (Math.random() * maxSize / 2 + maxSize / 2).toFloat()
        speed = (Math.random() * maxSpeed / 2 + maxSpeed / 2).toFloat()
        y = -size;
        x = (Math.random() * containerWidth).toFloat()
    }

    fun update() {
        y += speed
        if (y > containerHeight) {
            reset()
        }
    }

    fun draw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        val path = createSnowflakePath(size)
        canvas.save()
        canvas.translate(x, y)
        canvas.rotate(45f)
        canvas.drawPath(path, p)
        canvas.restore()
    }

    private fun createSnowflakePath(size: Float): Path {
        val path = Path()

        path.moveTo(0f, -size / 2)
        path.lineTo(0f, size / 2)

        path.moveTo(-size / 2, 0f)
        path.lineTo(size / 2, 0f)

        path.moveTo(-size / 2, -size / 2)
        path.lineTo(size / 2, size / 2)

        path.moveTo(size / 2, -size / 2)
        path.lineTo(-size / 2, size / 2)


        return path
    }

}
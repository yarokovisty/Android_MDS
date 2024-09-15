package com.example.lab24

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View


class SnowAnimation(context: Context) : View(context) {
    private lateinit var snowflakes: Array<Snowflake>

    @SuppressLint("DrawAllocation", "UseCompatLoadingForDrawables")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        snowflakes = Array(50) {
            Snowflake(
                right - left, bottom - top,
                75f,
                7.5f
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        snowflakes.forEach {
            it.update()
            it.draw(canvas)
        }
        postInvalidateOnAnimation()
    }
}
package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        for (i in 0 until bindingClass.gridLayout.childCount) {
            val child: View = bindingClass.gridLayout.getChildAt(i)
            child.setBackgroundColor(Color.argb(i*10, 0, 0, 0))

            child.setOnClickListener{
                onTextViewClick(child)
            }
        }
    }


    fun onTextViewClick(view: View) {
        view.setBackgroundColor(Color.argb(Random.nextInt(255), 0, 0, 0))
    }
}
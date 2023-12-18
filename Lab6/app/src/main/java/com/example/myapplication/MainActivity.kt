package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val root = findViewById<ConstraintLayout>(R.id.root)
        root.setBackgroundColor(Color.argb(255,
            Random.nextInt(255),
            Random.nextInt(255),
            Random.nextInt(255)))
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyLog", "Вызван метод onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyLog", "Вызван метод onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyLog", "Вызван метод onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyLog", "Вызван метод onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MyLog", "Вызван метод onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLog", "Вызван метод onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("MyLog", "Вызван метод onSaveInstanceState")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.d("MyLog", "Вызван метод onRestoreInsranceState")
    }
}


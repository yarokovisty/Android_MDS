package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var snackbar: Snackbar
    var textMessage = ""
    var BGR = 0
    var BGG = 0
    var BGB = 0
    var textR = 0
    var textG = 0
    var textB = 0
    var textAction = ""
    var actionR = 0
    var actionG = 0
    var actionB = 0
    var delay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.apply {
            swBackground.setOnCheckedChangeListener { _, _ ->
                etBGR.isEnabled = swBackground.isChecked
                etBGG.isEnabled = swBackground.isChecked
                etBGB.isEnabled = swBackground.isChecked
            }
            swColorText.setOnCheckedChangeListener{ _, _ ->
                etTextR.isEnabled = swColorText.isChecked
                etTextG.isEnabled = swColorText.isChecked
                etTextB.isEnabled = swColorText.isChecked
            }
            swAction.setOnCheckedChangeListener { _, _ ->
                etAction.isEnabled = swAction.isChecked
                etActionR.isEnabled = swAction.isChecked
                etActionG.isEnabled = swAction.isChecked
                etActionB.isEnabled = swAction.isChecked
            }

            textMessage = etMessage.text.toString()

            if (swBackground.isChecked) {
                BGR = etBGR.text.toString().toInt()
                BGG = etBGG.text.toString().toInt()
                BGB = etBGB.text.toString().toInt()
            }

            if (swColorText.isChecked) {
                textR = etTextR.text.toString().toInt()
                textG = etTextG.text.toString().toInt()
                textB = etTextB.text.toString().toInt()
            }

            if (swAction.isChecked) {
                textAction = etAction.text.toString()
                actionR = etActionR.text.toString().toInt()
                actionG = etActionG.text.toString().toInt()
                actionR = etActionR.text.toString().toInt()
            }

        }

    }

    fun onRadioButtonClicked(view: View) {
        when (view.id) {
            R.id.rbQuick -> delay = 3
            R.id.rbLong -> delay = 6
            R.id.rbConst -> delay = Snackbar.LENGTH_INDEFINITE
        }
    }

    fun onClickShow(view: View) {
        binding.apply {
            textMessage = etMessage.text.toString()

            if (swBackground.isChecked) {
                BGR = etBGR.text.toString().toInt()
                BGG = etBGG.text.toString().toInt()
                BGB = etBGB.text.toString().toInt()
            }

            if (swColorText.isChecked) {
                textR = etTextR.text.toString().toInt()
                textG = etTextG.text.toString().toInt()
                textB = etTextB.text.toString().toInt()
            }

            if (swAction.isChecked) {
                textAction = etAction.text.toString()
                actionR = etActionR.text.toString().toInt()
                actionG = etActionG.text.toString().toInt()
                actionR = etActionR.text.toString().toInt()
            }
        }

        snackbar = Snackbar.make(view, textMessage, delay)
        snackbar.setAction(textAction) {}
        snackbar.setBackgroundTint(Color.argb(255, BGR, BGG, BGB))
        snackbar.setTextColor(Color.argb(255, textR, textG, textB))
        snackbar.setActionTextColor(Color.argb(255, actionR, actionG, actionB))
        snackbar.show()
    }
}
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val items = arrayOf(
        Item(R.drawable.food01, true),
        Item(R.drawable.food02, true),
        Item(R.drawable.food03, true),
        Item(R.drawable.food04, true),
        Item(R.drawable.food05, true),
        Item(R.drawable.food06, true),
        Item(R.drawable.food07, true),
        Item(R.drawable.food08, true),
        Item(R.drawable.food09, true),
        Item(R.drawable.food10, true),
        Item(R.drawable.food11, true),
        Item(R.drawable.food12, true),
        Item(R.drawable.food13, true),
        Item(R.drawable.food14, true),
        Item(R.drawable.food15, true),
        Item(R.drawable.food16, true),
        Item(R.drawable.food17, true),
        Item(R.drawable.food18, true),
        Item(R.drawable.food19, true),
        Item(R.drawable.food20, true),
        Item(R.drawable.sport01, false),
        Item(R.drawable.sport02, false),
        Item(R.drawable.sport03, false),
        Item(R.drawable.sport04, false),
        Item(R.drawable.sport05, false),
        Item(R.drawable.sport06, false),
        Item(R.drawable.sport07, false),
        Item(R.drawable.sport08, false),
        Item(R.drawable.sport09, false),
        Item(R.drawable.sport10, false),
        Item(R.drawable.sport11, false),
        Item(R.drawable.sport12, false),
        Item(R.drawable.sport13, false),
        Item(R.drawable.sport14, false),
        Item(R.drawable.sport15, false),
        Item(R.drawable.sport16, false),
        Item(R.drawable.sport17, false),
        Item(R.drawable.sport18, false),
        Item(R.drawable.sport19, false),
        Item(R.drawable.sport20, false),
    )
    private var right = 0
    private var wrong = 0
    private var item = items[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            var text = getString(R.string.right_answers) + " " + right.toString()
            tvRight.text = text
            text = getString(R.string.wrong_answers) + " " + wrong.toString()
            tvWrong.text = text
        }
    }

    fun onClickEdible(view: View) {
        if (item.food) right++
        else wrong++

        item = items[Random.nextInt(0, 39)]
        binding.apply {
            ivFood.setImageResource(item.resId)
            var text = getString(R.string.right_answers) + " " + right.toString()
            tvRight.text = text
            text = getString(R.string.wrong_answers) + " " + wrong.toString()
            tvWrong.text = text
        }
    }

    fun onClickInEdible(view: View) {
        if (!item.food) right++
        else wrong++

        item = items[Random.nextInt(0, 39)]
        binding.apply {
            ivFood.setImageResource(item.resId)
            var text = getString(R.string.right_answers) + " " + right.toString()
            tvRight.text = text
            text = getString(R.string.wrong_answers) + " " + wrong.toString()
            tvWrong.text = text
        }
    }

}
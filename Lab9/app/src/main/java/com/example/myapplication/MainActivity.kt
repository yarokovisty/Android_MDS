package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun translate(num: Int): String {
        var n10 = num
        var n2 = ""

        if (num == 0) return "0"

        while (n10 != 0) {
            n2 += n10 % 2
            n10 /= 2
        }

        return n2.reversed()
    }

    fun onClickTranslate(view: View) = with(binding) {
        val num10 = etNum.text.toString().toInt()
        tvAnswer.visibility = View.VISIBLE
        tvAnswer.text = translate(num10)
    }

    fun onClickClear(view: View) = with(binding) {
        etNum.text.clear()
        tvAnswer.text = ""
        tvAnswer.visibility = View.INVISIBLE
    }
}
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val provider = ViewModelProvider(this)
        viewModel = provider[MainViewModel::class.java]

        viewModel.currencyR.observe(this) {
            if (binding.edValue.text.toString() != it.toString()) binding.edValue.setText(it.toString())
        }

        viewModel.currencyF.observe(this) {
            binding.tvResult.text = it.toString()
        }

        binding.apply {
            edValue.addTextChangedListener {
                val n = it.toString().toIntOrNull() ?: 0
                viewModel.currencyR.value = n
            }

            btnD.setOnClickListener {
                viewModel.currencyF.value = viewModel.currencyR.value?.div(90)
            }

            btnE.setOnClickListener {
                viewModel.currencyF.value = viewModel.currencyR.value?.div(100)
            }

            btnT.setOnClickListener {
                viewModel.currencyF.value = viewModel.currencyR.value?.times(5)
            }
        }



    }
}

class MainViewModel : ViewModel() {
    val currencyR = MutableLiveData<Int>()
    val currencyF = MutableLiveData<Int>()
}
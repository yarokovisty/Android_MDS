package com.example.lab13.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.lab13.R
import com.example.lab13.databinding.ActivityMainBinding
import com.example.lab13.domain.entity.City

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var city: City


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntent()
        observe()
        setupClickListener()

    }

    private fun navigationToActivity() {
        startActivity(ListCityActivity.newIntentListCity(this))
        finish()
    }

    private fun navigationToMap() {
        startActivity(newInstanceMap(city.lat, city.lon))
    }

    private fun parseIntent() {
        if (intent.hasExtra(CHOOSE_MODE)) {
            binding.llInfoCity.isVisible = true
            viewModel.getCityItem(intent.getIntExtra(CHOOSE_MODE, -1))
        }
    }

    private fun setupClickListener() = with(binding) {
        btnChooseCity.setOnClickListener {
            navigationToActivity()
        }
        btnShowCity.setOnClickListener {
            navigationToMap()
        }
    }

    private fun observe() {
        viewModel.cityItem.observe(this) {
            city = it
            showInfoCity(city)
        }
    }

    private fun showInfoCity(city: City) = with(city) {
        val strInfo = "Город: $title\n" +
                "Федеральный округ: $region\n" +
                "Регион: $district\n" +
                "Почтовый индекс: $postalCode\n" +
                "Часово пояс: $timezone\n" +
                "Население: $population\n" +
                "Основан в: $founded"
        binding.tvInfoCity.text = strInfo

    }

    companion object {
        private const val CHOOSE_MODE = "choose"

        fun newInstanceChoose(context: Context, mode: String, cityId: Int): Intent {
            val intent = Intent(context, MainActivity::class.java)

            return if (mode == CHOOSE_MODE) {
                intent.putExtra(CHOOSE_MODE, cityId)
            } else {
                intent
            }
        }

        fun newInstanceExit(context: Context) = Intent(context, MainActivity::class.java)

        private fun newInstanceMap(lat: Float, lon: Float) = Intent(Intent.ACTION_VIEW,
            Uri.parse("geo:$lat,$lon")
        )
    }
}
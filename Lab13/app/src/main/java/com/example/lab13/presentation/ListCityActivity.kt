package com.example.lab13.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.lab13.R
import com.example.lab13.data.RepositoryCityImpl
import com.example.lab13.databinding.ActivityListCityBinding

class ListCityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListCityBinding
    private lateinit var adapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupRecycleView()
        setupClickListener()
    }


    private fun setupRecycleView() {
        adapter = CityListAdapter()
        adapter.addCities(RepositoryCityImpl.cities)
        binding.rvListCity.adapter = adapter
    }

    private fun setupClickListener() {
        adapter.onCityItemClickListener = {cityId ->
            startActivity(MainActivity.newInstanceChoose(this, CHOOSE_MODE, cityId))
            finish()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(MainActivity.newInstanceExit(this@ListCityActivity))
                finish()
            }

        }
        onBackPressedDispatcher.addCallback(callback)
    }

    private fun loadData() = RepositoryCityImpl.initCities(this)


    companion object {
        private const val CHOOSE_MODE = "choose"

        fun newIntentListCity(context: Context) = Intent(context, ListCityActivity::class.java)
    }
}
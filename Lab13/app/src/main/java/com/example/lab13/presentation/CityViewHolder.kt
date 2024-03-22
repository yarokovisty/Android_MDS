package com.example.lab13.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lab13.databinding.ItemCityBinding
import com.example.lab13.domain.entity.City

class CityViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemCityBinding.bind(view)

    fun bind(city: City) = with(binding) {
        tvCity.text = city.title
        tvRegion.text = city.region
    }
}
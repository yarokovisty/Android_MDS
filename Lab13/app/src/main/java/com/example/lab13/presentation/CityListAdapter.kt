package com.example.lab13.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab13.R
import com.example.lab13.domain.entity.City

class CityListAdapter: RecyclerView.Adapter<CityViewHolder>() {
    private val listCity = mutableListOf<City>()
    var onCityItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = listCity[position]

        holder.bind(city)

        holder.itemView.setOnClickListener {
            onCityItemClickListener?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCities(cities: List<City>) {
        listCity.addAll(cities)
        notifyDataSetChanged()
    }
}
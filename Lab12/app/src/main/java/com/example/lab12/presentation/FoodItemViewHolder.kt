package com.example.lab12.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lab12.R
import com.example.lab12.databinding.ItemFoodEnabledBinding
import com.example.lab12.domain.FoodItem

class FoodItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFoodEnabledBinding.bind(view)
    private val context = view.context

    fun bind(foodItem: FoodItem) {
        binding.apply {
            iconFoodItem.setImageResource(foodItem.img)
            tvTitleFood.text = foodItem.name
            tvPriceFood.text =
                String.format(context.getString(R.string.text_price), foodItem.price);
        }
    }

}
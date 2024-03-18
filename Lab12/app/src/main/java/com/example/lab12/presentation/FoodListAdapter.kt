package com.example.lab12.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.lab12.R
import com.example.lab12.domain.FoodItem

class FoodListAdapter : ListAdapter<FoodItem, FoodItemViewHolder>(FoodItemDiffCallback()) {
    var onFoodItemClickListener: ((FoodItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_food_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_food_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem = getItem(position)

        holder.bind(foodItem)

        holder.itemView.setOnClickListener {
            onFoodItemClickListener?.invoke(foodItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 15
    }
}
package com.example.lab15.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.lab15.domain.entity.ProductItem

class ProductItemDiffCallback : DiffUtil.ItemCallback<ProductItem>() {
    override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
        return oldItem == newItem
    }
}
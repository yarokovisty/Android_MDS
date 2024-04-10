package com.example.lab15.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lab15.R
import com.example.lab15.databinding.ItemProductBinding
import com.example.lab15.domain.entity.ProductItem

class ProductItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemProductBinding.bind(view)

    fun bind(productItem: ProductItem) = with(binding) {
        tvNameProduct.text = productItem.name
        tvNumberProduct.text = String.format(
            view.context.getString(R.string.number_product),
            productItem.number
        )
    }
}
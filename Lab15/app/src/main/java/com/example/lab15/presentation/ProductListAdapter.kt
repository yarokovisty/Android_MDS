package com.example.lab15.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.lab15.R
import com.example.lab15.domain.entity.ProductItem


class ProductListAdapter : ListAdapter<ProductItem, ProductItemViewHolder>(ProductItemDiffCallback()) {
    var onProductItemClickListener: ((ProductItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val productItem = getItem(position)

        holder.bind(productItem)

        holder.itemView.setOnClickListener{
            onProductItemClickListener?.invoke(productItem)
        }

    }

}
package com.example.lab15.domain.repository

import androidx.lifecycle.LiveData
import com.example.lab15.domain.entity.ProductItem

interface ProductListRepository {
    fun addProductItem(productItem: ProductItem)

    fun deleteProductItem(productItem: ProductItem)

    fun editProductItem(productItem: ProductItem)

    fun getProductItem(productItemId: Int): ProductItem

    fun getProductList(): LiveData<List<ProductItem>>
}
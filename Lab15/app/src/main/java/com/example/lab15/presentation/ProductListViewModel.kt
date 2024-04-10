package com.example.lab15.presentation

import androidx.lifecycle.ViewModel
import com.example.lab15.data.ProductListRepositoryImpl
import com.example.lab15.domain.entity.ProductItem
import com.example.lab15.domain.usecase.DeleteProductItemUseCase
import com.example.lab15.domain.usecase.GetProductListUseCase

class ProductListViewModel : ViewModel() {
    private val repository = ProductListRepositoryImpl

    private val getProductListUseCase = GetProductListUseCase(repository)
    private val deleteProductItemUseCase = DeleteProductItemUseCase(repository)

    val productList = getProductListUseCase()

    fun deleteProductItem(productItem: ProductItem) {
        deleteProductItemUseCase(productItem)
    }
}
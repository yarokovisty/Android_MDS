package com.example.lab15.domain.usecase

import com.example.lab15.domain.entity.ProductItem
import com.example.lab15.domain.repository.ProductListRepository

class AddProductItemUseCase(private val repository: ProductListRepository) {
    operator fun invoke(productItem: ProductItem) = repository.addProductItem(productItem)
}
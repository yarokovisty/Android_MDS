package com.example.lab15.domain.usecase

import com.example.lab15.domain.entity.ProductItem
import com.example.lab15.domain.repository.ProductListRepository

class DeleteProductItemUseCase(private val repository: ProductListRepository)  {
    operator fun invoke(productItem: ProductItem) = repository.deleteProductItem(productItem)
}
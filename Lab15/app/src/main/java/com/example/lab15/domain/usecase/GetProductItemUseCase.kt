package com.example.lab15.domain.usecase

import com.example.lab15.domain.repository.ProductListRepository

class GetProductItemUseCase(private val repository: ProductListRepository)  {
    operator fun invoke(productItemId: Int) = repository.getProductItem(productItemId)
}
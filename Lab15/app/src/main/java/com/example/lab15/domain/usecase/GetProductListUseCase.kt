package com.example.lab15.domain.usecase

import com.example.lab15.domain.repository.ProductListRepository

class GetProductListUseCase(private val repository: ProductListRepository)  {
    operator fun invoke() = repository.getProductList()
}
package com.example.lab15.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab15.data.ProductListRepositoryImpl
import com.example.lab15.domain.entity.ProductItem
import com.example.lab15.domain.usecase.AddProductItemUseCase
import com.example.lab15.domain.usecase.EditProductItemUseCase
import com.example.lab15.domain.usecase.GetProductItemUseCase

class ProductItemViewModel : ViewModel() {
    private val repository = ProductListRepositoryImpl

    private val getProductItemUseCase = GetProductItemUseCase(repository)
    private val addProductItemUseCase = AddProductItemUseCase(repository)
    private val editProductItemUseCase = EditProductItemUseCase(repository)

    private val _productItem = MutableLiveData<ProductItem>()
    val productItem: LiveData<ProductItem>
        get() = _productItem

    fun getProductItem(productItemId: Int) {
        val item = getProductItemUseCase(productItemId)
        _productItem.value = item
    }

    fun addProductItem(inputName: String, inputNumber: String) {
        val productItem = ProductItem(inputName, inputNumber)
        addProductItemUseCase(productItem)
    }

    fun editProductItem(inputName: String, inputNumber: String) {
        _productItem.value?.let {
            val item = it.copy(name = inputName, number = inputNumber)
            editProductItemUseCase(item)
        }
    }
}
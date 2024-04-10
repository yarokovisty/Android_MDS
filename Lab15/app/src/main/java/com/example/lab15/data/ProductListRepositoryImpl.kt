package com.example.lab15.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab15.domain.repository.ProductListRepository
import com.example.lab15.domain.entity.ProductItem
import java.lang.RuntimeException

object ProductListRepositoryImpl: ProductListRepository {
    private val productListLD = MutableLiveData<List<ProductItem>>()
    private val productList = sortedSetOf<ProductItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    override fun addProductItem(productItem: ProductItem) {
        if (productItem.id == ProductItem.UNDEFINED_ID) {
            productItem.id = autoIncrementId++
        }
        productList.add(productItem)
        updateList()
    }

    override fun deleteProductItem(productItem: ProductItem) {
        productList.remove(productItem)
        updateList()
    }

    override fun editProductItem(productItem: ProductItem) {
        val oldElement = getProductItem(productItem.id)
        productList.remove(oldElement)
        addProductItem(productItem)
    }

    override fun getProductItem(productItemId: Int): ProductItem {
        return productList.find { it.id == productItemId }
            ?: throw RuntimeException("Element with id $productItemId not found")
    }

    override fun getProductList(): LiveData<List<ProductItem>> {
        return productListLD
    }

    private fun updateList() {
        productListLD.value = productList.toList()
    }


}
package com.example.lab12.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.lab12.data.FoodListRepositoryImpl
import com.example.lab12.domain.EditFoodItemUseCase
import com.example.lab12.domain.FoodItem
import com.example.lab12.domain.GetFoodListUseCase

class MainViewModel : ViewModel() {

    private val repository = FoodListRepositoryImpl

    private val getFoodListUseCase = GetFoodListUseCase(repository)
    private val editFoodItemUseCase = EditFoodItemUseCase(repository)

    val foodList = getFoodListUseCase.getFoodListUseCase()

    fun changeEnableState(foodItem: FoodItem) {
        val newItem = foodItem.copy(enabled = !foodItem.enabled)
        editFoodItemUseCase.editFoodItem(newItem)
    }
}
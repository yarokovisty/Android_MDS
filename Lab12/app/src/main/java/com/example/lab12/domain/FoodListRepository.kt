package com.example.lab12.domain

import androidx.lifecycle.LiveData

interface FoodListRepository {
    fun addFoodItem(foodItem: FoodItem)

    fun editFoodItem(foodItem: FoodItem)

    fun getFoodItem(foodItemId: Int): FoodItem

    fun getFoodList(): LiveData<List<FoodItem>>
}
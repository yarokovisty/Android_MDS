package com.example.lab12.domain

class AddFoodListUseCase(private val foodListRepository: FoodListRepository) {

    fun addFoodItem(foodItem: FoodItem) {
        foodListRepository.addFoodItem(foodItem)
    }
}
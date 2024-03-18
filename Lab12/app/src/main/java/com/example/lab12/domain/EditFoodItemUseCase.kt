package com.example.lab12.domain

class EditFoodItemUseCase(private val foodListRepository: FoodListRepository) {

    fun editFoodItem(foodItem: FoodItem) {
        foodListRepository.editFoodItem(foodItem)
    }
}
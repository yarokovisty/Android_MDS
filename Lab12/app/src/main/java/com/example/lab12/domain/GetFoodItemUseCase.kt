package com.example.lab12.domain

class GetFoodItemUseCase(private val foodListRepository: FoodListRepository) {

    fun getFoodItem(foodItemId: Int): FoodItem {
        return foodListRepository.getFoodItem(foodItemId)
    }
}
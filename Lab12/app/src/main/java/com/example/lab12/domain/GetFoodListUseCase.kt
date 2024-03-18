package com.example.lab12.domain

import androidx.lifecycle.LiveData

class GetFoodListUseCase(private val foodListRepository: FoodListRepository) {

    fun getFoodListUseCase(): LiveData<List<FoodItem>> {
        return foodListRepository.getFoodList()
    }
}
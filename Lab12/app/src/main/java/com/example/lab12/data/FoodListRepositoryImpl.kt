package com.example.lab12.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab12.R
import com.example.lab12.domain.FoodItem
import com.example.lab12.domain.FoodListRepository

object FoodListRepositoryImpl : FoodListRepository {
    private val foodListLD = MutableLiveData<List<FoodItem>>()
    private val foodList = sortedSetOf<FoodItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoIncrementId = 0

    init {
        for (i in 1..20) {
            addFoodItem(
                FoodItem(
                    R.drawable.food01,
                    "Арахисовая паста",
                    179.99,
                    true
                )
            )
        }


    }

    override fun addFoodItem(foodItem: FoodItem) {
        if (foodItem.id == FoodItem.UNDEFINED_ID) {
            foodItem.id = autoIncrementId++
        }
        foodList.add(foodItem)
        updateList()
    }

    override fun editFoodItem(foodItem: FoodItem) {
        Log.i("MyLog", "123")
        val oldElement = getFoodItem(foodItem.id)
        foodList.remove(oldElement)
        addFoodItem(foodItem)
    }

    override fun getFoodItem(foodItemId: Int): FoodItem {
        return foodList.find { it.id == foodItemId }
            ?: throw RuntimeException("Element with id $foodItemId not found")
    }

    override fun getFoodList(): LiveData<List<FoodItem>> {
        return foodListLD
    }

    private fun updateList() {
        foodListLD.value = foodList.toList()
    }


}
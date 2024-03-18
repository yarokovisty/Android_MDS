package com.example.lab12.domain

data class FoodItem(
    val img: Int,
    val name: String,
    val price: Double,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
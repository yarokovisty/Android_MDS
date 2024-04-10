package com.example.lab15.domain.entity

data class ProductItem(
    val name: String,
    val number: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
package com.example.lab25.domain.entity

data class BookItem(
    val id: Long = 0,
    val name: String,
    val author: String,
    val isRead: Boolean = false
)

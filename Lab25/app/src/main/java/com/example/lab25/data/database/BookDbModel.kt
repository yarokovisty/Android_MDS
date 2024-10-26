package com.example.lab25.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_item")
data class BookDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val author: String,
    val isRead: Boolean
)

package com.example.lab25.domain.repository

import com.example.lab25.domain.entity.BookItem
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBookList(): Flow<List<BookItem>>

    suspend fun getBookItem(bookId: Long): BookItem

    suspend fun addBookItem(bookItem: BookItem)

    suspend fun editBookItem(bookItem: BookItem)

    suspend fun deleteBookItem(bookItem: BookItem)
}
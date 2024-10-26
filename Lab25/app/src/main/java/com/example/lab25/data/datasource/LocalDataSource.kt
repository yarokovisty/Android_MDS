package com.example.lab25.data.datasource

import com.example.lab25.data.database.BookDbModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getBookList(): Flow<List<BookDbModel>>

    suspend fun getBook(bookId: Long): BookDbModel

    suspend fun addBook(bookDbModel: BookDbModel)

    suspend fun deleteBook(bookId: Long)
}
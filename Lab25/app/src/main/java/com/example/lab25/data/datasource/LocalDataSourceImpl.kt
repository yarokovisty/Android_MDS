package com.example.lab25.data.datasource

import com.example.lab25.data.database.BookDao
import com.example.lab25.data.database.BookDbModel
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val bookDao: BookDao
) : LocalDataSource {

    override suspend fun getBookList(): Flow<List<BookDbModel>> =
        bookDao.getBookList()

    override suspend fun getBook(bookId: Long): BookDbModel =
        bookDao.getBook(bookId)

    override suspend fun addBook(bookDbModel: BookDbModel) =
        bookDao.addBook(bookDbModel)

    override suspend fun deleteBook(bookId: Long) =
        bookDao.deleteBook(bookId)
}
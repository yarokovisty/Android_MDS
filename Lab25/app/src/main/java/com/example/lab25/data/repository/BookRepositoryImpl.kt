package com.example.lab25.data.repository

import com.example.lab25.data.datasource.LocalDataSource
import com.example.lab25.data.mapper.BookMapper
import com.example.lab25.domain.entity.BookItem
import com.example.lab25.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val mapper: BookMapper
) : BookRepository {

    override suspend fun getBookList(): Flow<List<BookItem>> =
        localDataSource.getBookList().map {
            it.map { bookDbModel -> mapper.mapBookDbModelToBookItem(bookDbModel) }
        }

    override suspend fun getBookItem(bookId: Long): BookItem =
        mapper.mapBookDbModelToBookItem(localDataSource.getBook(bookId))

    override suspend fun addBookItem(bookItem: BookItem) =
        localDataSource.addBook(mapper.mapBookItemToBookDbModel(bookItem))

    override suspend fun editBookItem(bookItem: BookItem) =
        localDataSource.addBook(mapper.mapBookItemToBookDbModel(bookItem))

    override suspend fun deleteBookItem(bookItem: BookItem) =
        localDataSource.deleteBook(bookItem.id)
}
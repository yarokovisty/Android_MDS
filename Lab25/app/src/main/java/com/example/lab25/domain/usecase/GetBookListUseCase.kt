package com.example.lab25.domain.usecase

import com.example.lab25.domain.entity.BookItem
import com.example.lab25.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow


class GetBookListUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(): Flow<List<BookItem>> = repository.getBookList()
}
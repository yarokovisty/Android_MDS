package com.example.lab25.domain.usecase

import com.example.lab25.domain.entity.BookItem
import com.example.lab25.domain.repository.BookRepository

class GetBookItemUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(bookId: Long): BookItem = repository.getBookItem(bookId)
}
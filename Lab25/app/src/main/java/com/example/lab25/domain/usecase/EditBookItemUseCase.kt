package com.example.lab25.domain.usecase

import com.example.lab25.domain.entity.BookItem
import com.example.lab25.domain.repository.BookRepository

class EditBookItemUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(bookItem: BookItem) = repository.editBookItem(bookItem)
}
package com.example.lab25.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab25.domain.entity.BookItem
import com.example.lab25.domain.usecase.DeleteBookItemUseCase
import com.example.lab25.domain.usecase.EditBookItemUseCase
import com.example.lab25.domain.usecase.GetBookListUseCase
import com.example.lab25.presentation.state.BookListState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListViewModel(
    private val getBookListUseCase: GetBookListUseCase,
    private val editBookItemUseCase: EditBookItemUseCase,
    private val deleteBookItemUseCase: DeleteBookItemUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<BookListState>(BookListState.Initial)
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = BookListState.Error
    }

    fun load() {
        viewModelScope.launch(exceptionHandler) {
            getBookListUseCase().collect { books ->
                val (readBooks, unreadBooks) = withContext(Dispatchers.Default) {
                    books.partition { it.isRead }
                }
                _state.value = BookListState.Success(unreadBooks, readBooks)
            }
        }
    }

    fun edit(bookItem: BookItem) {
        val editBook = bookItem.copy(isRead = !bookItem.isRead)

        viewModelScope.launch(exceptionHandler) {
            editBookItemUseCase(editBook)
        }
    }

    fun delete(bookItem: BookItem) {
        viewModelScope.launch(exceptionHandler) {
            deleteBookItemUseCase(bookItem)
        }
    }

}
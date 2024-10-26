package com.example.lab25.presentation.state

import com.example.lab25.domain.entity.BookItem

sealed interface BookListState {

    data object Initial : BookListState

    data object Loading : BookListState

    data object Error : BookListState

    data class Success(val bookListUnread: List<BookItem>, val bookListRead: List<BookItem>) : BookListState
}
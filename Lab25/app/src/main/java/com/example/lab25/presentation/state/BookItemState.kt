package com.example.lab25.presentation.state

import com.example.lab25.domain.entity.BookItem

sealed interface BookItemState {

    data object Loading : BookItemState

    data object Error : BookItemState

    data class EmptyInput(val nameInput: Boolean, val authorInput: Boolean) : BookItemState

    data class Reset(val resetName: Boolean, val resetAuthor: Boolean) : BookItemState

    data class Success(val bookItem: BookItem) : BookItemState

    data object Finish : BookItemState
}
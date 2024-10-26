package com.example.lab25.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab25.domain.entity.BookItem
import com.example.lab25.domain.usecase.AddBookItemUseCase
import com.example.lab25.domain.usecase.EditBookItemUseCase
import com.example.lab25.domain.usecase.GetBookItemUseCase
import com.example.lab25.presentation.state.BookItemState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class BookItemViewModel(
    private val getBookItemUseCase: GetBookItemUseCase,
    private val addBookItemUseCase: AddBookItemUseCase,
    private val editBookItemUseCase: EditBookItemUseCase
) : ViewModel() {

    private val _state = MutableLiveData<BookItemState>()
    val state: LiveData<BookItemState> = _state

    private var _bookItem: BookItem? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MyLog", throwable.message.toString())
        _state.value = BookItemState.Error
    }

    fun add(inputName: String?, inputAuthor: String?) {
        val name = parseInput(inputName)
        val author = parseInput(inputAuthor)
        val validate = validateInput(name, author)

        if (validate) {
            val bookItem = BookItem(name = name, author = author)

            viewModelScope.launch(exceptionHandler) {
                addBookItemUseCase(bookItem)
                _state.value = BookItemState.Finish
            }
        }
    }

    fun edit(inputName: String?, inputAuthor: String?) {
        val name = parseInput(inputName)
        val author = parseInput(inputAuthor)

        if (validateInput(name, author)) {
            _bookItem?.let {
                val item = it.copy(name = name, author = author)
                viewModelScope.launch(exceptionHandler) {
                    editBookItemUseCase(item)
                    _state.value = BookItemState.Finish
                }
            }
        }
    }

    fun load(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            val bookItem = getBookItemUseCase(id)
            _bookItem = bookItem
            _state.value = BookItemState.Success(bookItem)
        }
    }

    fun reset(resetName: Boolean = false, resetAuthor: Boolean = false) {
        _state.value = BookItemState.Reset(resetName, resetAuthor)
    }

    private fun parseInput(input: String?): String =
        input ?: ""

    private fun validateInput(name: String, author: String) : Boolean {
        val isNameEmpty = name.isBlank()
        val isAuthorEmpty = author.isBlank()

        _state.value = when {
            isNameEmpty && isAuthorEmpty -> BookItemState.EmptyInput(true, true)
            isNameEmpty -> BookItemState.EmptyInput(true, false)
            isAuthorEmpty -> BookItemState.EmptyInput(false, true)
            else -> BookItemState.EmptyInput(false, false)
        }

        return !(isNameEmpty || isAuthorEmpty)
    }


}
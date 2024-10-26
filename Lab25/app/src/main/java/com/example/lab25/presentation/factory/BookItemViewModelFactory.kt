package com.example.lab25.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab25.data.database.AppDatabase
import com.example.lab25.data.datasource.LocalDataSourceImpl
import com.example.lab25.data.mapper.BookMapper
import com.example.lab25.data.repository.BookRepositoryImpl
import com.example.lab25.domain.usecase.AddBookItemUseCase
import com.example.lab25.domain.usecase.EditBookItemUseCase
import com.example.lab25.domain.usecase.GetBookItemUseCase
import com.example.lab25.presentation.viewmodel.BookItemViewModel

class BookItemViewModelFactory(application: Application) : ViewModelProvider.Factory {

    private val bookDao by lazy(LazyThreadSafetyMode.NONE) {
        AppDatabase.getInstance(application).bookDao()
    }
    private val localDataSource by lazy(LazyThreadSafetyMode.NONE) {
        LocalDataSourceImpl(bookDao)
    }
    private val mapper = BookMapper()
    private val repository by lazy(LazyThreadSafetyMode.NONE) {
        BookRepositoryImpl(localDataSource, mapper)
    }

    private val getBookItemUseCase = GetBookItemUseCase(repository)
    private val addBookItemUseCase = AddBookItemUseCase(repository)
    private val editBookItemUseCase = EditBookItemUseCase(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookItemViewModel(
            getBookItemUseCase,
            addBookItemUseCase,
            editBookItemUseCase
        ) as T
    }
}
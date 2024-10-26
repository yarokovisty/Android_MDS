package com.example.lab25.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.lab25.R
import com.example.lab25.databinding.ActivityBookBinding
import com.example.lab25.domain.entity.BookItem
import com.example.lab25.presentation.factory.BookItemViewModelFactory
import com.example.lab25.presentation.state.BookItemState
import com.example.lab25.presentation.viewmodel.BookItemViewModel

class BookActivity : AppCompatActivity() {
    private lateinit var mode: String
    private var bookId: Long = UNKNOWN_ID

    private val viewModel by viewModels<BookItemViewModel> {
        BookItemViewModelFactory(application)
    }

    private lateinit var binding: ActivityBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseParams()
        launchRightMode()
        addTextChangeListener()
        observeViewModel()
    }

    private fun parseParams() {
        mode = intent.getStringExtra(SCREEN_MODE) ?: throw RuntimeException("Mode absents")

        if (mode == MODE_EDIT) {
            bookId = intent.getLongExtra(BOOK_ID, UNKNOWN_ID)
        }
    }

    private fun launchRightMode() {
        when(mode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
            else -> throw IllegalStateException(mode)
        }
    }

    private fun launchAddMode() = with(binding) {
        btnAdd.setOnClickListener {
            viewModel.add(editTextNameBook.text?.toString(), editTextAuthorBook.text?.toString())
        }
    }

    private fun launchEditMode() = with(binding) {
        viewModel.load(bookId)

        btnAdd.setOnClickListener {
            viewModel.edit(editTextNameBook.text?.toString(), editTextAuthorBook.text?.toString())
        }
    }

    private fun addTextChangeListener() = with(binding) {
        editTextNameBook.addTextChangedListener {
            viewModel.reset(resetName = true)
        }
        editTextAuthorBook.addTextChangedListener {
            viewModel.reset(resetAuthor = true)
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this, ::renderState)
    }

    private fun renderState(state: BookItemState) {
        when(state) {
            BookItemState.Loading -> {}
            BookItemState.Error -> renderError()
            BookItemState.Finish -> renderFinish()
            is BookItemState.Reset -> renderReset(state.resetName, state.resetAuthor)
            is BookItemState.EmptyInput -> renderEmpty(state.nameInput, state.authorInput)
            is BookItemState.Success -> renderSuccess(state.bookItem)
        }
    }

    private fun renderError() {
        val errorText = getString(R.string.error)
        Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderFinish() {
        this.finish()
    }

    private fun renderReset(resetName: Boolean, resetAuthor: Boolean) = with(binding) {
        if (resetName) layoutNameBook.error = null
        if (resetAuthor) layoutAuthorBook.error = null
    }

    private fun renderEmpty(isEmptyName: Boolean, isEmptyAuthor: Boolean) = with(binding) {
        val errorText = getString(R.string.empty_input)

        if (isEmptyName) layoutNameBook.error = errorText
        if (isEmptyAuthor) layoutAuthorBook.error = errorText
    }

    private fun renderSuccess(bookItem: BookItem) = with(binding) {
        editTextNameBook.setText(bookItem.name)
        editTextAuthorBook.setText(bookItem.author)
    }

    companion object {
        const val SCREEN_MODE = "screen_mode"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        const val BOOK_ID = "book_id"
        private const val UNKNOWN_ID = 0L
    }
}
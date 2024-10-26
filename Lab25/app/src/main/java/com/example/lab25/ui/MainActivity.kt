package com.example.lab25.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lab25.R

import com.example.lab25.databinding.ActivityMainBinding
import com.example.lab25.domain.entity.BookItem
import com.example.lab25.presentation.factory.BookListViewModelFactory
import com.example.lab25.presentation.state.BookListState
import com.example.lab25.presentation.viewmodel.BookListViewModel
import com.example.lab25.ui.adapter.BookListAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: BookListViewModel by viewModels {
        BookListViewModelFactory(application)
    }

    private lateinit var adapterUnread: BookListAdapter
    private lateinit var adapterRead: BookListAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()
        setupRecycler()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    private fun setOnClickListener() {
        binding.btnAddBook.setOnClickListener {
            navigateToBookItemAddMode()
        }
    }

    private fun setupRecycler() {
        adapterUnread = BookListAdapter()
        adapterRead = BookListAdapter()

        binding.recyclerListNewBook.adapter = adapterUnread
        binding.recyclerListReadBook.adapter = adapterRead

        setupClickListenerItem()
        setupLongClickListenerItem()
        setupSwipeListener()
    }

    private fun setupClickListenerItem() {
        adapterUnread.onBookItemClickListener = { bookItem ->
            navigateToBookItemEditMode(bookItem.id)
        }
        adapterRead.onBookItemClickListener = { bookItem ->
            navigateToBookItemEditMode(bookItem.id)
        }
    }

    private fun setupLongClickListenerItem() {
        adapterUnread.onBookItemLongClickListener = { bookItem ->
            viewModel.edit(bookItem)
        }
        adapterRead.onBookItemLongClickListener = { bookItem ->
            viewModel.edit(bookItem)
        }
    }

    private fun setupSwipeListener() {
        val callbackUnread = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterUnread.currentList[viewHolder.adapterPosition]
                viewModel.delete(item)
            }
        }

        val callbackRead = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterRead.currentList[viewHolder.adapterPosition]
                viewModel.delete(item)
            }
        }

        val itemUnreadTouchHelper = ItemTouchHelper(callbackUnread)
        val itemReadTouchHelper = ItemTouchHelper(callbackRead)
        itemUnreadTouchHelper.attachToRecyclerView(binding.recyclerListNewBook)
        itemReadTouchHelper.attachToRecyclerView(binding.recyclerListReadBook)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state) {
                    BookListState.Initial -> {}
                    BookListState.Loading -> {}
                    BookListState.Error -> renderError()
                    is BookListState.Success -> renderSuccess(state.bookListUnread, state.bookListRead)
                }
            }
        }
    }

    private fun renderError() {
        val errorText = getString(R.string.error)
        Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderSuccess(unread: List<BookItem>, read: List<BookItem>) {
        adapterUnread.submitList(unread)
        adapterRead.submitList(read)
    }

    private fun navigateToBookItemAddMode() {
        val intent = Intent(this, BookActivity::class.java).apply {
            putExtra(BookActivity.SCREEN_MODE, BookActivity.MODE_ADD)
        }
        startActivity(intent)
    }

    private fun navigateToBookItemEditMode(bookId: Long) {
        val intent = Intent(this, BookActivity::class.java).apply {
            putExtra(BookActivity.SCREEN_MODE, BookActivity.MODE_EDIT)
            putExtra(BookActivity.BOOK_ID, bookId)
        }
        startActivity(intent)
    }
}
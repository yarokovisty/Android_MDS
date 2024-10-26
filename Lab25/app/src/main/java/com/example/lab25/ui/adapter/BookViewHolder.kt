package com.example.lab25.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lab25.R
import com.example.lab25.databinding.ItemBookBinding
import com.example.lab25.domain.entity.BookItem

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemBookBinding.bind(view)

    fun bind(bookItem: BookItem) = with(binding) {
        textTitleBook.text = bookItem.name
        textAuthorBook.text = bookItem.author

        if (bookItem.isRead) {
            iconBook.setImageResource(R.drawable.ic_read_book)
        } else {
            iconBook.setImageResource(R.drawable.ic_new_book)
        }
    }
}
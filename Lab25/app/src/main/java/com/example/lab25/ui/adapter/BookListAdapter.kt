package com.example.lab25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.lab25.R
import com.example.lab25.domain.entity.BookItem

class BookListAdapter : ListAdapter<BookItem, BookViewHolder>(BookItemCallback()) {
    var onBookItemClickListener: ((BookItem) -> Unit)? = null
    var onBookItemLongClickListener: ((BookItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)

        holder.itemView.setOnClickListener {
            onBookItemClickListener?.invoke(item)
        }

        holder.itemView.setOnLongClickListener {
            onBookItemLongClickListener?.invoke(item)
            true
        }
    }
}
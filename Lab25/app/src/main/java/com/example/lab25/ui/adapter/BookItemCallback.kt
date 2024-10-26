package com.example.lab25.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.lab25.domain.entity.BookItem

class BookItemCallback : DiffUtil.ItemCallback<BookItem>() {

    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
        oldItem == newItem
}
package com.example.lab25.data.mapper

import com.example.lab25.data.database.BookDbModel
import com.example.lab25.domain.entity.BookItem

class BookMapper {

    fun mapBookDbModelToBookItem(bookDbModel: BookDbModel): BookItem =
        BookItem(
            id = bookDbModel.id,
            name = bookDbModel.name,
            author = bookDbModel.author,
            isRead = bookDbModel.isRead
        )

    fun mapBookItemToBookDbModel(bookItem: BookItem): BookDbModel =
        BookDbModel(
            id = bookItem.id,
            name = bookItem.name,
            author = bookItem.author,
            isRead = bookItem.isRead
        )
}
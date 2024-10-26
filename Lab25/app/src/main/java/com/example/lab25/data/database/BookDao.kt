package com.example.lab25.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_item")
    fun getBookList(): Flow<List<BookDbModel>>

    @Query("SELECT * FROM book_item WHERE id=:bookId LIMIT 1")
    suspend fun getBook(bookId: Long): BookDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(bookDbModel: BookDbModel)

    @Query("DELETE FROM book_item WHERE id=:bookId")
    suspend fun deleteBook(bookId: Long)
}
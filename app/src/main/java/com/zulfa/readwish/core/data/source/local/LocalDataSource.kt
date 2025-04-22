package com.zulfa.readwish.core.data.source.local

import com.zulfa.readwish.core.data.source.local.entity.BookEntity
import com.zulfa.readwish.core.data.source.local.room.BookDao
import kotlinx.coroutines.flow.Flow



class LocalDataSource(private val bookDao: BookDao)  {

    fun getAllBook(): Flow<List<BookEntity>> = bookDao.getAllBook()

    fun getFavoriteBook(): Flow<List<BookEntity>> = bookDao.getFavoriteBook()

    fun getBooksByTypes(type: String): Flow<List<BookEntity>> = bookDao.getBooksByTypes(type)

    suspend fun insertBook(bookList: List<BookEntity>) = bookDao.insertBook(bookList)

    fun setFavoriteBook(book: BookEntity, newState: Boolean){
        book.isFavorite = newState
        bookDao.updateFavoriteBook(book)
    }
}
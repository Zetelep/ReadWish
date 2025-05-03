package com.zulfa.readwish.core.domain.repository

import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface IBookRepository {

    fun getAllBook(): Flow<Resource<List<Book>>>

    fun getAllBookByTypes(topic: String, sort:String): Flow<Resource<List<Book>>>

    fun searchBooks(query: String): Flow<Resource<List<Book>>>

    fun getFavoriteBook(): Flow<List<Book>>

    fun setFavoriteBook(book: Book  , state: Boolean)


}
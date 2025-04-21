package com.zulfa.readwish.core.domain.usecase

import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookUseCase {

    fun getAllBook(): Flow<Resource<List<Book>>>

    fun getAllBookByTypes(topic: String): Flow<Resource<List<Book>>>

    fun searchBooks(query: String): Flow<Resource<List<Book>>>

    fun getFavoriteBook(): Flow<List<Book>>

    fun setFavoriteBook(book: Book, state: Boolean)
}
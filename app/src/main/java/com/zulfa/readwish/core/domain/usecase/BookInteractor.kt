package com.zulfa.readwish.core.domain.usecase

import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.domain.repository.IBookRepository
import kotlinx.coroutines.flow.Flow

class BookInteractor(private val bookRepository: IBookRepository): BookUseCase {
    override fun getAllBook(): Flow<Resource<List<Book>>> = bookRepository.getAllBook()

    override fun getAllBookByTypes(topic: String, sort: String): Flow<Resource<List<Book>>> = bookRepository.getAllBookByTypes(topic,sort)

    override fun searchBooks(query: String): Flow<Resource<List<Book>>> = bookRepository.searchBooks(query)

    override fun searchBooksByTopic(query: String): Flow<Resource<List<Book>>> = bookRepository.searchBooksByTopic(query)

    override fun getFavoriteBook(): Flow<List<Book>> = bookRepository.getFavoriteBook()

    override fun setFavoriteBook(book: Book, state: Boolean) = bookRepository.setFavoriteBook(book, state)

    override suspend fun saveBook(book: Book) {
        bookRepository.saveBook(book)
    }

}
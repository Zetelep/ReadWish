package com.zulfa.readwish.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.domain.usecase.BookUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val bookUseCase: BookUseCase): ViewModel() {
    fun setFavoriteBook(book: Book, newStatus:Boolean) =
        bookUseCase.setFavoriteBook(book, newStatus)

    fun saveBookToLocal(book: Book) {
        viewModelScope.launch {
            bookUseCase.saveBook(book)
        }
    }

}
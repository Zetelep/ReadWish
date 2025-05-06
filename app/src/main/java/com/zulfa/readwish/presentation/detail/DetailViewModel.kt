package com.zulfa.readwish.presentation.detail

import androidx.lifecycle.ViewModel
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.domain.usecase.BookUseCase

class DetailViewModel(private val bookUseCase: BookUseCase): ViewModel() {
    fun setFavoriteBook(book: Book, newStatus:Boolean) =
        bookUseCase.setFavoriteBook(book, newStatus)

}
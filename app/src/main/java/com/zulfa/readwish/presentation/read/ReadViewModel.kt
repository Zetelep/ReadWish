package com.zulfa.readwish.presentation.read

import androidx.lifecycle.ViewModel
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.domain.usecase.BookUseCase

class ReadViewModel(private val bookUseCase: BookUseCase): ViewModel() {
    fun setFavoriteBook(book: Book, newStatus:Boolean) =
        bookUseCase.setFavoriteBook(book, newStatus)
}
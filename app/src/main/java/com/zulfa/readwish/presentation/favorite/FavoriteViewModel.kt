package com.zulfa.readwish.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zulfa.readwish.core.domain.usecase.BookUseCase

class FavoriteViewModel(bookUseCase: BookUseCase) : ViewModel() {
    val favoriteBook = bookUseCase.getFavoriteBook().asLiveData()
}
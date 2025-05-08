package com.zulfa.readwish.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.domain.usecase.BookUseCase
class SearchViewModel(private val bookUseCase: BookUseCase) : ViewModel() {

    fun searchBooks(query: String): LiveData<Resource<List<Book>>> {
        return bookUseCase.searchBooks(query).asLiveData()
    }

    fun searchBooksByTopic(query: String): LiveData<Resource<List<Book>>> {
        return bookUseCase.searchBooksByTopic(query).asLiveData()
    }
}
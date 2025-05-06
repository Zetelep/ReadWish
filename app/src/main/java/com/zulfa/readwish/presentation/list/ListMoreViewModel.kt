package com.zulfa.readwish.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zulfa.readwish.core.domain.usecase.BookUseCase

class ListMoreViewModel(
    private val topic: String,
    private val sort: String,
    private val bookUseCase: BookUseCase
) : ViewModel() {

    val books = bookUseCase.getAllBookByTypes(topic, sort)
        .asLiveData()
}
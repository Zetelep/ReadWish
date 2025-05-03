package com.zulfa.readwish.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zulfa.readwish.core.domain.usecase.BookUseCase

class HomeViewModel(bookUseCase: BookUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
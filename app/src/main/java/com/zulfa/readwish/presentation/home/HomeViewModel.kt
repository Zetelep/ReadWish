package com.zulfa.readwish.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zulfa.readwish.core.domain.usecase.BookUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(bookUseCase: BookUseCase) : ViewModel() {
        private val _selectedTopic = MutableStateFlow("all")

        val defaultBooks = _selectedTopic.flatMapLatest { topic ->
            bookUseCase.getAllBookByTypes(topic, "")
        }.asLiveData()

        val ascendingBooks = _selectedTopic.flatMapLatest { topic ->
            bookUseCase.getAllBookByTypes(topic, "ascending")
        }.asLiveData()

        val descendingBooks = _selectedTopic.flatMapLatest { topic ->
            bookUseCase.getAllBookByTypes(topic, "descending")
        }.asLiveData()

        fun selectTopic(topic: String) {
            _selectedTopic.value = topic
        }
}
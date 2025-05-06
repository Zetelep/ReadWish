package com.zulfa.readwish.presentation.di

import com.zulfa.readwish.core.domain.usecase.BookInteractor
import com.zulfa.readwish.core.domain.usecase.BookUseCase
import com.zulfa.readwish.presentation.detail.DetailViewModel
import com.zulfa.readwish.presentation.favorite.FavoriteViewModel
import com.zulfa.readwish.presentation.home.HomeViewModel
import com.zulfa.readwish.presentation.list.ListMoreViewModel
import com.zulfa.readwish.presentation.read.ReadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<BookUseCase> { BookInteractor(get()) }
}

val viewModelModule = module {
    viewModel{HomeViewModel(get()) }
    viewModel{DetailViewModel(get())}
    viewModel{FavoriteViewModel(get())}
    viewModel { (topic: String, sort: String) ->
        ListMoreViewModel(topic, sort, get())
    }
    viewModel{ReadViewModel(get())}


}
package com.zulfa.readwish.presentation.di

import com.zulfa.readwish.core.domain.usecase.BookInteractor
import com.zulfa.readwish.core.domain.usecase.BookUseCase
import com.zulfa.readwish.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<BookUseCase> { BookInteractor(get()) }
}

val viewModelModule = module {
    viewModel{HomeViewModel(get()) }
}
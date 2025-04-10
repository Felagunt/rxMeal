package com.example.rxmeal.di

import com.example.rxmeal.domain.repository.MealRepository
import com.example.rxmeal.domain.use_case.SearchUseCase
import com.example.rxmeal.model.MealApiService
import com.example.rxmeal.model.MealRepositoryImpl
import com.example.rxmeal.viewModel.MealViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

val appModule = module {
    singleOf(::MealRepositoryImpl).bind<MealRepository>()
    single { SearchUseCase(get()) }
    viewModelOf(::MealViewModel)
}
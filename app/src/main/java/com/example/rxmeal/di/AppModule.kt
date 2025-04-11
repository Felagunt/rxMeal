package com.example.rxmeal.di

import com.example.rxmeal.data.repository.MealLocalRepositoryImpl
import com.example.rxmeal.data.repository.MealRepositoryImpl
import com.example.rxmeal.domain.repository.MealLocalRepository
import com.example.rxmeal.domain.repository.MealRepository
import com.example.rxmeal.domain.use_case.DeleteMealFromLocalUseCase
import com.example.rxmeal.domain.use_case.GetAllFromLocalUseCase
import com.example.rxmeal.domain.use_case.SearchUseCase
import com.example.rxmeal.domain.use_case.UpsertMealLocalUseCase
import com.example.rxmeal.presentation.search_meal.viewModel.MealViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::MealRepositoryImpl).bind<MealRepository>()
    singleOf(::MealLocalRepositoryImpl).bind<MealLocalRepository>()
    single { SearchUseCase(get()) }
    single { DeleteMealFromLocalUseCase(get()) }
    single { UpsertMealLocalUseCase(get()) }
    single { GetAllFromLocalUseCase(get()) }
    viewModelOf(::MealViewModel)
}
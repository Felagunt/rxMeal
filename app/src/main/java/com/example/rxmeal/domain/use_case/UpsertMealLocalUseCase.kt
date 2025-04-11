package com.example.rxmeal.domain.use_case

import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.repository.MealLocalRepository
import io.reactivex.rxjava3.core.Completable

class UpsertMealLocalUseCase(
    private val repository: MealLocalRepository
) {
    fun execute(meal: Meal): Completable {
        return repository.upsert(meal)
    }
}
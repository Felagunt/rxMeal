package com.example.rxmeal.domain.use_case

import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.repository.MealLocalRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class DeleteMealFromLocalUseCase(
    private val repository: MealLocalRepository
) {
        fun execute(meal: Meal): Completable {
            return repository.deleteMeal(meal)
        }
}
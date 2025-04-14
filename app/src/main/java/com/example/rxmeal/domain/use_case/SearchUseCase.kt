package com.example.rxmeal.domain.use_case

import com.example.rxmeal.data.mapper.toMeal
import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.repository.MealRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchUseCase(
    private val repository: MealRepository
) {
    fun execute(query: String): Observable<List<Meal>> {
        return repository.search(query).map {response ->
            response.meals.map { mealDto ->
                mealDto.toMeal()
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {  }
    }
}
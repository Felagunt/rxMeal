package com.example.rxmeal.domain.use_case

import com.example.rxmeal.data.dto.MealResponse
import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.repository.MealLocalRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class GetAllFromLocalUseCase(
    private val repository: MealLocalRepository
) {
    fun execute(): Observable<List<Meal>> {
        return repository.getAllMeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
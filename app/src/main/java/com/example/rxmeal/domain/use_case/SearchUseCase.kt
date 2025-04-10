package com.example.rxmeal.domain.use_case

import com.example.rxmeal.domain.repository.MealRepository
import com.example.rxmeal.model.dto.MealResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchUseCase(
    private val repository: MealRepository
) {
    fun execute(query: String): Observable<MealResponse> {
        return repository.search(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
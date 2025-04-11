package com.example.rxmeal.domain.repository

import com.example.rxmeal.domain.model.Meal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MealLocalRepository {

    fun upsert(meal: Meal): Completable

    fun getAllMeals(): Observable<List<Meal>>

    fun deleteMeal(meal: Meal): Completable
}
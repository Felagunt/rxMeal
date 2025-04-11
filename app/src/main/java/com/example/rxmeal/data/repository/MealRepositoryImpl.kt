package com.example.rxmeal.data.repository

import com.example.rxmeal.data.remote.MealApiService
import com.example.rxmeal.domain.repository.MealRepository

class MealRepositoryImpl(
    private val api: MealApiService
): MealRepository {

    override fun search(q: String) = api.search(q)
}
package com.example.rxmeal.model

import com.example.rxmeal.domain.repository.MealRepository

class MealRepositoryImpl(
    private val api: MealApiService
): MealRepository {

    override fun search(q: String) = api.search(q)
}
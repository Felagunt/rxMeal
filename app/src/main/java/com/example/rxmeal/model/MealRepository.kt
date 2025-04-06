package com.example.rxmeal.model

class MealRepository {

    private val api: MealApiService by lazy { RetrofitInstance.provideApiService() }

    fun search(q: String) = api.search(q)
}
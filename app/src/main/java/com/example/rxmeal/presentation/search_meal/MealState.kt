package com.example.rxmeal.presentation.search_meal

import com.example.rxmeal.domain.model.Meal

data class MealState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val meals: List<Meal> = emptyList(),
    val favorites: List<Meal> = emptyList(),
    val errorMsg: String? = null
)
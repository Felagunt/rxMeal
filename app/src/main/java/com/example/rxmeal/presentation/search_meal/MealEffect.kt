package com.example.rxmeal.presentation.search_meal

sealed class MealEffect {
    data class ShowToast(val message: String) : MealEffect()
}
package com.example.rxmeal.presentation.search_meal

import com.example.rxmeal.domain.model.Meal

sealed class MealAction {
    data class OnSearchQueryChange(val query: String) : MealAction()
    data class OnUpsertClick(val meal: Meal) : MealAction()
    data class OnDeleteClick(val meal: Meal) : MealAction()
}
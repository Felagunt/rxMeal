package com.example.rxmeal.data.dto

data class MealDto(
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealAlternate: String? = null,
    val strMealThumb: String,
    val strTags: String? = null,
    val strYoutube: String
)
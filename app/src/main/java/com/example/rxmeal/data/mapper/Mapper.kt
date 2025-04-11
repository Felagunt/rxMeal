package com.example.rxmeal.data.mapper

import com.example.rxmeal.data.dto.MealDto
import com.example.rxmeal.data.local.MealEntity
import com.example.rxmeal.domain.model.Meal

fun MealEntity.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealAlternate = strMealAlternate,
        strMealThumb = strMealThumb,
        strTags = strTags,
        strYoutube = strYoutube
    )
}

fun MealDto.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealAlternate = strMealAlternate,
        strMealThumb = strMealThumb,
        strTags = strTags,
        strYoutube = strYoutube
    )
}
fun Meal.toMealEntity(): MealEntity {
    return MealEntity(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealAlternate = strMealAlternate,
        strMealThumb = strMealThumb,
        strTags = strTags,
        strYoutube = strYoutube
    )
}
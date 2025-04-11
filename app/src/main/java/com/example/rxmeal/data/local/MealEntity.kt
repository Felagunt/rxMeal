package com.example.rxmeal.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealEntity(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealAlternate: Any,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String
)
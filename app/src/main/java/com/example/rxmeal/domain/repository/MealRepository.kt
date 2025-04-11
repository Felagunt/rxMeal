package com.example.rxmeal.domain.repository

import com.example.rxmeal.data.dto.MealResponse
import io.reactivex.rxjava3.core.Observable

interface MealRepository {
    fun search(query: String): Observable<MealResponse>
}
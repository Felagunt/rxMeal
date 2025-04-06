package com.example.rxmeal.model

import com.example.rxmeal.model.dto.MealResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {


    @GET("api/json/v1/1/search.php")
    fun search(@Query("s") query: String): Observable<MealResponse>
}
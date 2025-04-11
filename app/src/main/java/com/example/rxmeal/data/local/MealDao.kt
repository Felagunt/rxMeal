package com.example.rxmeal.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface MealDao{

    @Upsert
    fun upsert(mealEntity: MealEntity): Completable

    @Query("SELECT * FROM mealentity")
    fun getAllMeals(): Observable<List<MealEntity>>

    @Delete
    fun deleteMeal(mealEntity: MealEntity): Completable
}

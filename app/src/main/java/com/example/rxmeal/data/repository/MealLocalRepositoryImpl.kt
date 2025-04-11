package com.example.rxmeal.data.repository

import com.example.rxmeal.data.local.MealDao
import com.example.rxmeal.data.mapper.toMeal
import com.example.rxmeal.data.mapper.toMealEntity
import com.example.rxmeal.domain.model.Meal
import com.example.rxmeal.domain.repository.MealLocalRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MealLocalRepositoryImpl(
    private val dao: MealDao
): MealLocalRepository {

    override fun upsert(meal: Meal): Completable {
        return dao.upsert(meal.toMealEntity())
    }

    override fun getAllMeals(): Observable<List<Meal>> {
        return dao.getAllMeals().map {list ->
            list.map {elem ->
                elem.toMeal()
            }
        }
    }

    override fun deleteMeal(meal: Meal): Completable {
        return dao.deleteMeal(meal.toMealEntity())
    }


}
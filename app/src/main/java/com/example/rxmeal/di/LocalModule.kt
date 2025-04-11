package com.example.rxmeal.di

import androidx.room.Room
import com.example.rxmeal.data.local.MealDao
import com.example.rxmeal.data.local.MealDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            MealDatabase::class.java,
            "meal.db"
        ).build()
    }

    single<MealDao> {
        val database = get<MealDatabase>()
        database.getMealDao()
    }
}
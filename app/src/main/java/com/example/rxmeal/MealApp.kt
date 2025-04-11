package com.example.rxmeal

import android.app.Application
import com.example.rxmeal.di.appModule
import com.example.rxmeal.di.localModule
import com.example.rxmeal.di.networkModule
import org.koin.core.context.startKoin

class MealApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule, networkModule, localModule)
        }
    }
}
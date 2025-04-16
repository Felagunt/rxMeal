package com.example.rxmeal

import android.app.Application
import android.content.Context
import com.example.rxmeal.di.appModule
import com.example.rxmeal.di.localModule
import com.example.rxmeal.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MealApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoinApp(this)
    }
    fun startKoinApp(context: Context) {
        startKoin {
            androidContext(context)//TODO
            modules(appModule, networkModule, localModule)
        }
    }
}
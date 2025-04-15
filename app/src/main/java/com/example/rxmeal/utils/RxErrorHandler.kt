package com.example.rxmeal.utils

import retrofit2.HttpException
import java.io.IOException

object RxErrorHandler {
    fun handle(throwable: Throwable): String {
        return when (throwable) {
            is IOException -> "Проблема с интернетом"
            is HttpException -> "Ошибка сервера: ${throwable.code()}"
            else -> throwable.message ?: "Неизвестная ошибка"
        }
    }
}
package com.example.todolist.utils

sealed class Response<out T> {
    class Loading<out T>: Response<T>()
    data class Success<out T>(val data: T): Response<T>()
    data class Failure<out T>(val message: String): Response<T>()
}
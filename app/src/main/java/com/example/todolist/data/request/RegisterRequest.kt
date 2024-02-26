package com.example.todolist.data.request

data class RegisterRequest(
    val name: String,
    val lastName: String,
    val userName: String,
    val bornDate: String,
    val email: String,
    val password: String
)

fun RegisterRequest.toRegisterRequest() = RegisterRequest(
    name, lastName, userName, bornDate, email, password
)



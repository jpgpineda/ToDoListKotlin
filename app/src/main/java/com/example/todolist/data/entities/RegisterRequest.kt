package com.example.todolist.data.entities

import java.util.Date

data class RegisterRequest(
    val name: String,
    val lastName: String,
    val userName: String,
    val bornDate: Date,
    val email: String,
    val password: String
)

fun RegisterRequest.toRegisterRequest() = RegisterRequest(
    name, lastName, userName, bornDate, email, password
)



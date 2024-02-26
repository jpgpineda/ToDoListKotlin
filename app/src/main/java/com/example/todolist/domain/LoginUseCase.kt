package com.example.todolist.domain

import com.example.todolist.data.request.LoginRequest
import com.example.todolist.data.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(parameters: LoginRequest) = repository.login(parameters)
}
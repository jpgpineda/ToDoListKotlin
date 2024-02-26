package com.example.todolist.domain

import com.example.todolist.data.request.PersonalInfoRequest
import com.example.todolist.data.request.RegisterRequest
import com.example.todolist.data.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {

    suspend operator fun invoke(parameters: RegisterRequest) = repository.register(parameters)
    suspend fun savePersonalInfo(parameters: PersonalInfoRequest) = repository.savePersonalInfo(parameters)
}
package com.example.todolist.domain

import com.example.todolist.data.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {

    fun register() {

    }
}
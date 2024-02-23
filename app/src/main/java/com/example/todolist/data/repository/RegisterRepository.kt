package com.example.todolist.data.repository

import com.example.todolist.data.entities.RegisterRequest
import com.example.todolist.utils.Response
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RegisterRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    suspend fun register(parameters: RegisterRequest) = flow {
        emit(Response.Loading())

        val response = kotlin.runCatching {
            auth.createUserWithEmailAndPassword(parameters.email, parameters.password)
        }

        emit(response.fold(
            onSuccess = { Response.Success(it)},
            onFailure = { error ->
                Response.Failure(
                    error.message ?: "Hubo un error"
                )}
        ))
    }.catch { error ->
        emit(Response.Failure(error.message ?: "Hubo un error"))
    }
}
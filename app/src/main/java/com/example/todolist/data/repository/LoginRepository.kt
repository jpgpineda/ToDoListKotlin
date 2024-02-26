package com.example.todolist.data.repository

import com.example.todolist.data.request.LoginRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.getDefaultErrorMessage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.tasks.await

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth) {
    suspend fun login(paramaters: LoginRequest) = flow {
        emit(Response.Loading())

        val response = kotlin.runCatching {
            auth.signInWithEmailAndPassword(paramaters.email, paramaters.password).await()
        }

        emit(response.fold(
            onSuccess = { Response.Success(it) },
            onFailure = { error ->
                Response.Failure(error.message ?: String().getDefaultErrorMessage())}
        ))
    }.catch {error ->
        emit(Response.Failure(error.message ?: String().getDefaultErrorMessage()))
    }
}

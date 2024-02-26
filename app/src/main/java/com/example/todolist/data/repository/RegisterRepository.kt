package com.example.todolist.data.repository

import com.example.todolist.data.request.PersonalInfoRequest
import com.example.todolist.data.request.RegisterRequest
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.getDefaultErrorMessage
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

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
                    error.message ?: String().getDefaultErrorMessage()
                )}
        ))
    }.catch { error ->
        emit(Response.Failure(error.message ?: String().getDefaultErrorMessage()))
    }

    suspend fun savePersonalInfo(parameters: PersonalInfoRequest) = flow {
        emit(Response.Loading())
        val response = kotlin.runCatching {
            auth.currentUser?.let {
                db.collection("USERS").document(it.uid).set(parameters.propertiesToMap(), SetOptions.merge()).await()
            }
        }

        emit(response.fold(
            onSuccess = { Response.Success(it) },
            onFailure = { error ->
                Response.Failure(error.message ?: String().getDefaultErrorMessage())
            }
        ))
    }.catch {
        emit(Response.Failure(it.message ?: String().getDefaultErrorMessage()))
    }
}
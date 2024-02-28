package com.example.todolist.data.repository

import com.example.todolist.data.retrofit.apiClient.CharacterApiClient
import com.example.todolist.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject
import com.example.todolist.utils.Response.Loading
import com.example.todolist.utils.extensions.getDefaultErrorMessage
import kotlinx.coroutines.flow.flow

class CharacterRepository @Inject constructor(
    private val retrofit: Retrofit
) {
    suspend fun getCharacters() = flow {
        emit(Loading())
        val response = kotlin.runCatching {
            retrofit.create(CharacterApiClient::class.java).getCharacters()
        }

        emit(response.fold(
            onSuccess = { Response.Success(it)},
            onFailure = { Response.Failure(it.message ?: String().getDefaultErrorMessage())}
        ))
    }
}
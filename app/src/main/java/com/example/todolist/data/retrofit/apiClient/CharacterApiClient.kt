package com.example.todolist.data.retrofit.apiClient

import com.example.todolist.data.entities.Character
import com.example.todolist.data.entities.CharacterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface CharacterApiClient {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>
}
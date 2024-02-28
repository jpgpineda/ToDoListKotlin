package com.example.todolist.data.entities

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("gender")
    val gender: String
)

data class CharacterResponse(
    @SerializedName("results")
    val results: List<Character>
)

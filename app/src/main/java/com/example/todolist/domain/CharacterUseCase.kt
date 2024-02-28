package com.example.todolist.domain

import com.example.todolist.data.entities.Character
import com.example.todolist.data.repository.CharacterRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend fun getCharacters() = repository.getCharacters()
}
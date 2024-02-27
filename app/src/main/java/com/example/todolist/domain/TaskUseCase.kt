package com.example.todolist.domain

import com.example.todolist.data.entities.Task
import com.example.todolist.data.repository.TaskRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun getEventsFromFirebase() = repository.getTaskFromFirebase()

    suspend fun saveNewTask(parameters: Task) = repository.addTaskToFirebase(parameters)
}
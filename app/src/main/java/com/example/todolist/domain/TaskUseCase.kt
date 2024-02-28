package com.example.todolist.domain

import com.example.todolist.data.entities.Task
import com.example.todolist.data.repository.TaskRepository
import com.example.todolist.data.room.models.TaskModel
import javax.inject.Inject

class TaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun getEventsFromFirebase() = repository.getTaskFromFirebase()

    suspend fun saveNewTask(parameters: Task) = repository.addTaskToFirebase(parameters)

    suspend fun saveTaskLocally(tasks: List<TaskModel>) = repository.saveTaskLocally(tasks)

    suspend fun getTaskFromDatabase() = repository.getTaskFromDatabase()
}
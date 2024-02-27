package com.example.todolist.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "task_table")
data class TaskModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val dueDate: Long,
    val isCompleted: Boolean
)
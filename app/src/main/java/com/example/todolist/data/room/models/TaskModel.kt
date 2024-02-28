package com.example.todolist.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.data.entities.Task
import com.example.todolist.utils.extensions.dateToTimeStamp
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

fun TaskModel.toTask() = Task(
    id = id,
    title = title,
    description = description,
    dueDate = Date(dueDate).dateToTimeStamp(),
    isCompleted = isCompleted
)
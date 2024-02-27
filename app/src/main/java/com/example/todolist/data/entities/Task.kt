package com.example.todolist.data.entities

import com.example.todolist.utils.interfaces.Mappable
import java.util.Date

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: Date,
    val isCompleted: Boolean
): Mappable

//enum class TaskStatus(rawValue: String) {
//    COMPLETED("completo"),
//    PENDING("pendiente");
//
//    companion object  {
//        fun typeStatus(text: String) = TaskStatus.values().find {
//            it.raw == text
//        }
//    }
//}
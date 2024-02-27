package com.example.todolist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.data.room.dao.TaskDAO
import com.example.todolist.data.room.models.TaskModel

@Database(entities = [TaskModel::class], version = 1)
abstract class ToDoListDatabase: RoomDatabase() {
    abstract fun taskDAO(): TaskDAO
}
package com.example.todolist.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.todolist.data.room.models.TaskModel

@Dao
abstract class TaskDAO: BaseDAO<TaskModel>("task_table") {
    @Query("SELECT * FROM task_table")
    abstract fun getTasks(): List<TaskModel>?

    @Query("DELETE FROM task_table WHERE id = :id")
    abstract fun deleteTaskById(id: String)
}
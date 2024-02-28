package com.example.todolist.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class BaseDAO<T>(private val tableName: String) {
    @Insert
    abstract fun insert(model: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertModels(models: List<T>)

    @RawQuery
    abstract fun deleteAllModels(query: SupportSQLiteQuery): Int

    open fun deleteAll() {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName")
        deleteAllModels(query)
    }

    @Update
    abstract fun update(models: List<T>)

    @Update
    abstract fun updateModel(model: T)

    @Delete
    abstract fun delete(model: T)
}
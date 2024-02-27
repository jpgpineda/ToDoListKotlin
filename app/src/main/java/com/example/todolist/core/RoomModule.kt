package com.example.todolist.core

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.room.ToDoListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val TO_DO_LIST_DATABASE = "ToDoList-database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, ToDoListDatabase::class.java, TO_DO_LIST_DATABASE).build()

    @Singleton
    @Provides
    fun provideTaskDAO(db: ToDoListDatabase) = db.taskDAO()
}
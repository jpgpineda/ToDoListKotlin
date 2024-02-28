package com.example.todolist.data.repository

import com.example.todolist.data.entities.Task
import com.example.todolist.data.room.dao.TaskDAO
import com.example.todolist.data.room.models.TaskModel
import com.example.todolist.data.room.models.toTask
import com.example.todolist.utils.Response
import com.example.todolist.utils.constants.FirebaseConstants
import com.example.todolist.utils.extensions.createTaskFromQuery
import com.example.todolist.utils.extensions.getDefaultErrorMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val taskDAO: TaskDAO
) {
    suspend fun getTaskFromFirebase() = flow {
        val response = kotlin.runCatching {
            auth.currentUser?.let { currentUser ->
                db.collection(FirebaseConstants.USERS_COLLECTION)
                    .document(currentUser.uid)
                    .collection(FirebaseConstants.TASK_COLLECTION)
                    .get()
                    .await()
            }
        }

        emit(response.fold(
            onSuccess = { query ->
                val tasks = query?.map { it.createTaskFromQuery() }
                Response.Success(tasks)
            },
            onFailure = { error ->
                Response.Failure(
                    error.message ?: String().getDefaultErrorMessage()
                )
            }
        ))
    }.catch {
        emit(Response.Failure(it.message ?: String().getDefaultErrorMessage()))
    }

    suspend fun addTaskToFirebase(parameters: Task) = flow {
        emit(Response.Loading())

        val response = kotlin.runCatching {
            auth.currentUser?.let { currentUser ->
                db.collection(FirebaseConstants.USERS_COLLECTION)
                    .document(currentUser.uid)
                    .collection(FirebaseConstants.TASK_COLLECTION)
                    .document(parameters.id)
                    .set(parameters.propertiesToMap(), SetOptions.merge()).await()
            }
        }

        emit(response.fold(
            onSuccess = { Response.Success(it)},
            onFailure = { Response.Failure(it.message ?: String().getDefaultErrorMessage())
            }
        ))
    }.catch {
        emit(Response.Failure(it.message ?: String().getDefaultErrorMessage()))
    }
    suspend fun saveTaskLocally(tasks: List<TaskModel>) = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            taskDAO.insertModels(tasks)
        }.fold(
            onSuccess = { Response.Success(it) },
            onFailure = { error ->
                Response.Failure(error.message ?: String().getDefaultErrorMessage())
            }
        )
    }

    suspend fun getTaskFromDatabase() = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            taskDAO.getTasks()?.map { it.toTask() }
        }.fold(
            onSuccess = { Response.Success(it) },
            onFailure = { Response.Failure(it.message ?: String().getDefaultErrorMessage()) }
        )
    }
}
package com.example.todolist.presentation.ui.activities.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todolist.data.entities.Task
import com.example.todolist.data.entities.toModel
import com.example.todolist.domain.TaskUseCase
import com.example.todolist.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
): ViewModel() {
    val getTaskList = MutableLiveData<List<Task>>()
    fun getTask(isUpdate: Boolean) = liveData(Dispatchers.IO) {
        emit(Response.Loading())
        val localTaskResponse = taskUseCase.getTaskFromDatabase()
        if (localTaskResponse is Response.Success && !localTaskResponse.data.isNullOrEmpty() && !isUpdate) {
            emit(localTaskResponse)
        } else {
            taskUseCase.getEventsFromFirebase().collect { response ->
                emit(response)
            }
        }
    }

    fun setTaskData(tasks: List<Task>) {
        getTaskList.value = tasks
    }

    fun saveTaskLocally(tasks: List<Task>) = liveData(Dispatchers.IO) {
        emit(taskUseCase.saveTaskLocally(tasks.map { it.toModel() }))
    }
}
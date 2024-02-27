package com.example.todolist.presentation.ui.activities.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todolist.data.entities.Task
import com.example.todolist.domain.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
): ViewModel() {
    val getTaskList = MutableLiveData<List<Task>>()
    fun getTask() = liveData(Dispatchers.IO) {
        taskUseCase.getEventsFromFirebase().collect { response ->
            emit(response)
        }
    }

    fun setTaskData(tasks: List<Task>) {
        getTaskList.value = tasks
    }
}
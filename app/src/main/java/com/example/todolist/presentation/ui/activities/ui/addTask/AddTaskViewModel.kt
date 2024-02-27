package com.example.todolist.presentation.ui.activities.ui.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todolist.data.entities.Task
import com.example.todolist.domain.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val useCase: TaskUseCase
) : ViewModel() {

    fun saveNewTask(parameters: Task) = liveData(Dispatchers.IO) {
        useCase.saveNewTask(parameters).collect { response ->
            emit(response)
        }
    }
}
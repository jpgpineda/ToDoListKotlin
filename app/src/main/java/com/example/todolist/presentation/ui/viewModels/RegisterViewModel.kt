package com.example.todolist.presentation.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todolist.data.entities.RegisterRequest
import com.example.todolist.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: RegisterUseCase) : ViewModel() {

    fun register(parameters: RegisterRequest) = liveData(Dispatchers.IO) {
        registerUserUseCase.invoke(parameters).collect { response ->
            withContext(Dispatchers.Main) {
                emit(response)
            }
        }
    }
}
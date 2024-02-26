package com.example.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todolist.data.request.LoginRequest
import com.example.todolist.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    fun login(parameters: LoginRequest) = liveData(Dispatchers.IO) {
        loginUseCase.invoke(parameters).collect { response ->
            emit(response)
        }
    }
}
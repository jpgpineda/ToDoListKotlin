package com.example.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todolist.data.request.LoginRequest
import com.example.todolist.data.request.PersonalInfoRequest
import com.example.todolist.data.request.RegisterRequest
import com.example.todolist.domain.LoginUseCase
import com.example.todolist.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase) : ViewModel() {

    fun register(parameters: RegisterRequest) = liveData(Dispatchers.IO) {
        registerUserUseCase.invoke(parameters).collect { response ->
            withContext(Dispatchers.Main) {
                emit(response)
            }
        }
    }

    fun savePersonalInfo(parameters: PersonalInfoRequest) = liveData(Dispatchers.IO) {
        registerUserUseCase.savePersonalInfo(parameters).collect { response ->
            withContext(Dispatchers.Main) {
                emit(response)
            }
        }
    }

    fun login(parameters: LoginRequest) = liveData(Dispatchers.IO) {
        loginUseCase.invoke(parameters).collect { response ->
            withContext(Dispatchers.Main) {
                emit(response)
            }
        }
    }
}
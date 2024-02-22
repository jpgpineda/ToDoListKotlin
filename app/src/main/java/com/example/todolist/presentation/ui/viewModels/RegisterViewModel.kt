package com.example.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.todolist.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val useCase: RegisterUseCase) : ViewModel() {

    fun register() {
        useCase.register()
    }
}
package com.example.todolist.presentation.ui.activities.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.entities.Character
import com.example.todolist.domain.CharacterUseCase
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.getDefaultErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {
    val charactersList = MutableLiveData<List<Character>>()

    fun getCharacters() = liveData(Dispatchers.IO) {
        characterUseCase.getCharacters().collect { response ->
            emit(response)
        }
    }

    fun setData(characters: List<Character>) {
        charactersList.value = characters
    }
}
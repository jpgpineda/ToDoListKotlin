package com.example.todolist.data.request

import com.example.todolist.utils.interfaces.Mappable
import com.google.firebase.Timestamp

data class PersonalInfoRequest(
    val name: String,
    val lastName: String,
    val userName: String,
    val bornDate: Timestamp,
): Mappable

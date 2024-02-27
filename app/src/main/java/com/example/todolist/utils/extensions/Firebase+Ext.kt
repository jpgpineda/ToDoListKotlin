package com.example.todolist.utils.extensions

import com.example.todolist.data.entities.Task
import com.example.todolist.utils.constants.FirebaseConstants
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.Date

fun QueryDocumentSnapshot.createTaskFromQuery() = Task(
    id = getString(FirebaseConstants.ID) ?: "",
    title = getString(FirebaseConstants.TITLE) ?: "",
    description = getString(FirebaseConstants.DESCRIPTION) ?: "",
    dueDate = getDate(FirebaseConstants.DUE_DATE) ?: Date(),
    isCompleted = getBoolean(FirebaseConstants.ISCOMPLETED) ?: false
)
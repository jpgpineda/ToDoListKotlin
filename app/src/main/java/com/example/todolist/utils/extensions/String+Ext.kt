package com.example.todolist.utils.extensions

fun String.capitalizeFirstLetter(): String {
    return if (isNotEmpty()) {
        this[0].uppercaseChar() + substring(1)
    } else {
        this
    }
}

fun String.getDefaultErrorMessage(): String {
    return "Hubo un error"
}

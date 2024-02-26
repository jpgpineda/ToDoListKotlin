package com.example.todolist.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.shortDate(): String {
    val format = SimpleDateFormat("dd/mm/yyyy", Locale("es", "ES"))
    return format.format(this)
}
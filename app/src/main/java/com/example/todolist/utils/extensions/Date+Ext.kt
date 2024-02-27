package com.example.todolist.utils.extensions

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.shortDate(): String {
    val format = SimpleDateFormat("dd/mm/yyyy", Locale("es", "ES"))
    return format.format(this)
}

fun Date.dateToTimeStamp(): Timestamp {
    return Timestamp(this)
}

fun Timestamp.TimeStampToDate(): Date {
    return this.toDate()
}
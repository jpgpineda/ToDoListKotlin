package com.example.todolist.utils.extensions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard() }
}

fun Fragment.makeToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    activity?.let {
        Toast.makeText(it, message, duration).show()
    }
}

fun Fragment.snack(message: String, lenght: Int = Snackbar.LENGTH_SHORT) {
    view?.run { Snackbar.make(this, message, lenght).show() }
}
package com.example.todolist.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.alert(dialogBuilder: MaterialAlertDialogBuilder.() -> Unit) {
    MaterialAlertDialogBuilder(this)
        .apply {
            setCancelable(false)
            dialogBuilder()
            create()
            show()
        }
}
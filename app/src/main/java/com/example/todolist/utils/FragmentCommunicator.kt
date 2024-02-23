package com.example.todolist.utils

interface FragmentCommunicator {
    fun showLoader(isVisible: Boolean)
    fun showMessageAlert(title: String, message: String)
}
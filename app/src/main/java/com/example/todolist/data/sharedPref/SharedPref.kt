package com.example.todolist.data.sharedPref

import androidx.preference.PreferenceManager
import com.example.todolist.presentation.ui.activities.OnboardingActivity

enum class UserKeys(val rawValue: String) {
    LastSignedUser("lastSignedUser")
}

object SharedPref {
    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OnboardingActivity.appContext)

    val lastSignedUser: String
        get() = sharedPreferences.getString(UserKeys.LastSignedUser.rawValue, "").toString()

    fun saveLastSignedUser(email: String?) {
        sharedPreferences.edit().putString(UserKeys.LastSignedUser.rawValue, email).apply()
    }
}
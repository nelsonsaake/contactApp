package com.ampersand.contactapp

import androidx.lifecycle.ViewModel

class LogInViewModel : ViewModel() {

    val repo = LogInRepo()

    fun login(email : String, password : String) {

        repo.login(email, password)
    }
}
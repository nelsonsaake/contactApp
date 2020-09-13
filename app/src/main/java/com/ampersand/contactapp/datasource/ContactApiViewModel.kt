package com.ampersand.contactapp.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ContactApiViewModel : ViewModel() {

    private val repo = ContactApiRepo()

    fun login(email : String, password : String) {

        repo.login(email, password)
    }

    fun getProfile(): LiveData<User> {

        return repo.profile
    }
}
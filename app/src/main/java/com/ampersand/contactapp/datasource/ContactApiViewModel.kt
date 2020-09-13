package com.ampersand.contactapp.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody

class ContactApiViewModel : ViewModel() {

    private val repo = ContactApiRepo()

    val loginError = repo.loginError
    val regError = repo.regError

    fun login(email: String, password: String): LiveData<Boolean> {

        return repo.login(email, password)
    }

    fun getProfile(): LiveData<User> {

        return repo.profile
    }

    fun register(regRequestBody: RegRequestBody): LiveData<Boolean> {

        return repo.register(regRequestBody)
    }
}
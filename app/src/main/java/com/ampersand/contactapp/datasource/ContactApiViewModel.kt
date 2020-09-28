package com.ampersand.contactapp.datasource

import androidx.lifecycle.*
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody

class ContactApiViewModel : ViewModel() {

    private val repo = ContactApiRepo()

    val loginError = repo.loginError
    val regError = repo.regError

    fun login(email: String, password: String): LiveData<Boolean> {

        return repo.fakerLogin(email, password)
    }

    fun encode(raw: String): String {
        // use some encrypting algorithm to encrypt the raw
        // and later decrypt
        return raw
    }

    fun decode(code: String): String {
        return code
    }

    fun getProfile(userCode: String): LiveData<User> {

        val email = decode(userCode)
        return repo.fakerProfile(email)
    }

    fun register(regRequestBody: RegRequestBody): LiveData<Boolean> {

        return repo.fakerRegister(regRequestBody)
    }

    fun addContact(lifecylcleOwner: LifecycleOwner, userCode: String) {

        /*
        * right now all the data is fake, so we don't want to actually save anything
        *
        * */

//        val email = decode(userCode)
//        repo.profile(email).observe(lifecylcleOwner, Observer { profile ->
//
//            PhoneBook()
//                .addContact(
//                    profile.firstName,
//                    profile.lastName,
//                    profile.phoneNumber,
//                    profile.email,
//                    profile.role
//                )
//        })
    }

    fun userCode(): String {

        var email = ""
        if(LoggedInUser.user != null){
            email = LoggedInUser.user?.email as String
        } else {
             throw(Exception("User is null"))
        }
        return encode(email)
    }
}
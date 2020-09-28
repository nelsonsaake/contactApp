package com.ampersand.contactapp.datasource

import androidx.lifecycle.*
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody

class ContactViewModel : ViewModel() {

    private val repo = ContactRepo()
    val loginError = repo.loginError
    val regError = repo.regError

    fun encode(raw: String): String {

        // use some encrypting algorithm to encrypt the raw
        // and later decrypt
        return raw
    }

    fun decode(code: String): String {

        // decode is called to decrypt the scanned code to a user email
        // api provided to support the app is not available so
        // we fake a decode
        return repo.fakerUser().email
    }

    fun login(email: String, password: String): LiveData<Boolean> {

        return repo.fakerLogin(email, password)
    }

    fun profile(userCode: String): LiveData<User> {

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
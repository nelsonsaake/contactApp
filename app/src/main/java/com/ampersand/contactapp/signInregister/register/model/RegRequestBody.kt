package com.ampersand.contactapp.signInregister.register.model

data class RegRequestBody(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val photo: String,
    val role: String,
    val phoneNumber: String,
    val twitter: String,
    val linkedIn: String,
    val website: String
){
    fun isValid() : Boolean {

        if(!isEmailValid()) return false;

        if(!isPasswordValid()) return false;

        if(firstName.isNullOrEmpty()) return false;

        if(lastName.isNullOrEmpty()) return false;

        if(phoneNumber.isNullOrEmpty()) return false;

        return true
    }

    fun isEmailValid(): Boolean {

        val acceptableDomains = arrayOf("@ampersandllc.co", "@gmail.com")
        acceptableDomains.forEach{ domain ->
            if(email.endsWith(domain, true)) return true
        }

        return false
    }

    fun isPasswordValid(): Boolean {

        return (password.length > 5)
    }
}

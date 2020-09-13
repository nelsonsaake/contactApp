package com.ampersand.contactapp.signInregister.register.model

data class RegRequestBody(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val phoneNumber: String,
    val twitter: String,
    val linkedIn: String,
    val website: String,
    val photo: String
)

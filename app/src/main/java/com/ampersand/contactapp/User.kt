package com.ampersand.contactapp

data class User(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val photo: String,
    val phoneNumber: String,
    val twitter: String,
    val linkedIn: String,
    val website: String
)

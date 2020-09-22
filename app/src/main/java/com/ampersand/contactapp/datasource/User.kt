package com.ampersand.contactapp.datasource

data class User(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val photo: String,
    val role: String,
    val twitter: String,
    val linkedIn: String,
    val website: String
)

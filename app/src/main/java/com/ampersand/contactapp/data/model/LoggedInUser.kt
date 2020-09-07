package com.ampersand.contactapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(

    var token: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var role: String,
    var photo: String,
    var twitter: String,
    var linkedIn: String,
    var website: String
)
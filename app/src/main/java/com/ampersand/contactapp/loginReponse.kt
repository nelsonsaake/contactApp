package com.ampersand.contactapp


data class User (

    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var phoneNumber: String?,
    var role: String?,
    var photo: String?,
    var twitter: String?,
    var linkedIn: String?,
    var website: String?
)

data class LoginResponse (

    var token: String?,
    var user: User?
)
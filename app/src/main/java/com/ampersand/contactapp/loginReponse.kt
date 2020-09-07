package com.ampersand.contactapp

import com.ampersand.contactapp.data.model.LoggedInUser

data class LoginResponse (

    var token: String?,
    var user: LoggedInUser?
)
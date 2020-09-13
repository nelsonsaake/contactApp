package com.ampersand.contactapp.signInregister.signin.model

import com.ampersand.contactapp.datasource.User

data class LogInResponse(val token: String, val user: User)

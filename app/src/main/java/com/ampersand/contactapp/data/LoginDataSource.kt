package com.ampersand.contactapp.data

import com.ampersand.contactapp.LoginApiService
import com.ampersand.contactapp.LoginResponse
import com.ampersand.contactapp.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val loginApiService = LoginApiService.create()
    var loginResponse: LoginResponse? = null

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            // so I will make an API call here
            // using the user name and password
            // if It was successfull, I just log the user in otherwise I through and error
            // that's it
            loginApiService.login(username, password).enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    throw t
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.code() != 200) throw Throwable("failed to get user")
                    loginResponse = response.body()
                }
            })

            var name = "nelson"
            name = loginResponse?.user?.firstName!!

            val user = LoggedInUser(java.util.UUID.randomUUID().toString(), name)
            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
    }
}
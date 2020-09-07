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

            var user: LoggedInUser? = null
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

                    if( loginResponse != null) {

                        user = loginResponse!!.user!!
                        user?.token = loginResponse!!.token!!
                    }
                }
            })

            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
    }
}
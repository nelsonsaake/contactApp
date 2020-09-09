package com.ampersand.contactapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInRepo {

     val countryApiService = ContactApiService.create()
     val loggedInUser = MutableLiveData<LoggedInUser>()
     val loginError = MutableLiveData<String>()
     val regError = MutableLiveData<String>()

    val regCallback = object : Callback<RegResponse> {

        override fun onFailure(call: Call<RegResponse>, t: Throwable) {
            Log.e(
                LOG_TAG,
                "msg: ${t.localizedMessage}; \n localised msg: ${t.message}\n"
            )
        }

        override fun onResponse(
            call: Call<RegResponse>,
            response: Response<RegResponse>
        ) {

            val regResponse = response.body()
            val status = regResponse?.status

            val gson = Gson()
            if (status == "201") {

                val json = gson.toJson(regResponse.response)
                val reg201Res = gson.fromJson(json, Reg201Response::class.java)
                loggedInUser.value = reg201Res as LoggedInUser
            } else {

                val json = gson.toJson(regResponse?.response)
                val regErr = gson.fromJson(json, RegErrResponse::class.java)
                val err = "error registering user; msg: ${regErr.error}\n"
                Log.e(LOG_TAG, err)
                regError.value = err
            }
        }
    }

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        photo: String,
        phoneNumber: String,
        twitter: String,
        linkedIn: String,
        website: String
    ) {

        val regRequestBody = RegRequestBody(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName,
            photo = photo,
            phoneNumber = phoneNumber,
            twitter = twitter,
            linkedIn = linkedIn,
            website = website
        )
        countryApiService.register(regRequestBody).enqueue(regCallback)
    }

    val loginCallback = object : Callback<LoginResponse> {

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            val localisedMsg = "localised msg: ${t.localizedMessage}"
            val msg = "msg: ${t.message}"
            Log.e( LOG_TAG,"$localisedMsg\n $msg\n")
            loginError.value = msg
        }

        override fun onResponse(
            call: Call<LoginResponse>,
            response: Response<LoginResponse>
        ) {

            loggedInUser.value = response.body() as LoggedInUser
        }
    }

    fun login(email: String, password: String) {

        val loginRequestBody = LoginRequestBody(email, password)
        countryApiService.login(loginRequestBody).enqueue(loginCallback)
    }



}
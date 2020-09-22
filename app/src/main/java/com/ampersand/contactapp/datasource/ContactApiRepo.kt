package com.ampersand.contactapp.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ampersand.contactapp.signInregister.register.model.Reg201Response
import com.ampersand.contactapp.signInregister.register.model.RegErrResponse
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody
import com.ampersand.contactapp.signInregister.register.model.RegResponse
import com.ampersand.contactapp.signInregister.signin.model.LogInRequestBody
import com.ampersand.contactapp.signInregister.signin.model.LogInResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactApiRepo {

    val contactApiService = ContactApiService.create()
    val loginError = MutableLiveData<String>()
    val regError = MutableLiveData<String>()
    val isLoginSuccessful = MutableLiveData<Boolean>()
    val isReggeredSuccessful = MutableLiveData<Boolean>()

    init {

        loginError.value = ""
        regError.value = ""
        isLoginSuccessful.value = false
        isReggeredSuccessful.value = false
    }

    fun onApiCallFailure(t: Throwable){
        Log.e(
            LOG_TAG,
            "msg: ${t.localizedMessage}; \n localised msg: ${t.message}\n"
        )
    }

    fun register(regRequestBody: RegRequestBody): LiveData<Boolean> {

        val regCallback = object : Callback<RegResponse> {

            override fun onFailure(call: Call<RegResponse>, t: Throwable) = onApiCallFailure(t)

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
                    LoggedInUser.token = reg201Res.token
                    LoggedInUser.user = reg201Res.user
                } else {

                    val json = gson.toJson(regResponse?.response)
                    val regErr = gson.fromJson(json, RegErrResponse::class.java)
                    val err = "error registering user; msg: ${regErr.error}\n"
                    Log.e(LOG_TAG, err)
                    regError.value = err
                }
            }
        }
        contactApiService.register(regRequestBody).enqueue(regCallback)
        return isReggeredSuccessful
    }

    fun login(email: String, password: String): LiveData<Boolean> {

        val loginCallback = object : Callback<LogInResponse> {

            override fun onFailure(call: Call<LogInResponse>, t: Throwable) {

                val localisedMsg = "localised msg: ${t.localizedMessage}"
                val msg = "msg: ${t.message}"
                Log.e(LOG_TAG, "$localisedMsg\n $msg\n")
                loginError.value = msg
            }

            override fun onResponse(
                call: Call<LogInResponse>,
                response: Response<LogInResponse>
            ) {

                LoggedInUser.token = response.body()?.token
                LoggedInUser.user = response.body()?.user
                isLoginSuccessful.value = true
            }
        }
        val loginRequestBody = LogInRequestBody(email, password)
        contactApiService.login(loginRequestBody).enqueue(loginCallback)
        return isLoginSuccessful
    }

    fun getProfile(email: String): LiveData<User>{

        var profile = MutableLiveData<User>()

        contactApiService.profile(email).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable)  = onApiCallFailure(t)

            override fun onResponse(call: Call<User>, response: Response<User>) {

                profile.value = response.body()
            }
        })
        return profile
    }
}
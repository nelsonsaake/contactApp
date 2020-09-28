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

class ContactRepo {

    val contactApiService = ContactApiService.create()
    val loginError = MutableLiveData<String>()
    val regError = MutableLiveData<String>()
    val isLoginSuccessful = MutableLiveData<Boolean>()
    val isReggeredSuccessful = MutableLiveData<Boolean>()

    init {

        isLoginSuccessful.value = false
        isReggeredSuccessful.value = false
        fakerFactory()
    }

    fun onApiCallFailure(t: Throwable) {
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

    fun profile(email: String): LiveData<User> {

        var profile = MutableLiveData<User>()

        contactApiService.profile(email).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) = onApiCallFailure(t)

            override fun onResponse(call: Call<User>, response: Response<User>) {

                profile.value = response.body()
            }
        })
        return profile
    }

    // the api provided is not available
    // so fake data is used instead
    // faker
    private var fakerUsers = mutableMapOf<String, User>()

    private fun fakerName(): String {

        val names = arrayOf(
            "Nelson",
            "James",
            "John",
            "Zebedy",
            "Mahlakai",
            "Joana",
            "Aristotel",
            "Kiyosaki",
            "Robert",
            "Nyce",
            "Rajesh",
            "Omid",
            "Armin"
        )
        return names.random()
    }

    private fun fakerPhoto(): String {

        val photoIndex = (1..8)
        val location = "@drawable/"
        return location + "photo" + photoIndex.random()
    }

    private fun fakerRole(): String {

        val roles = arrayOf(
            "Supervisor",
            "Backend Developer",
            "Android Developer",
            "Full Stack developer",
            "IOS Developer",
            "Human Resource Personnel",
            "Director",
            "Project Manager"
        )

        return roles.random()
    }

    fun fakerUser(): User {

        val firstName = fakerName()
        val lastName = fakerName()
        val email = firstName + lastName + "@gmail.com"

        return User(
            email,
            "$email password",
            firstName,
            lastName,
            "0548876758",
            fakerPhoto(),
            fakerRole(),
            "",
            "",
            ""
        )
    }

    private fun fakerUserWithEmailAndPassword(email: String, password: String) : User {

        var user = fakerUsers[email]
        if (user != null) return user

        user = User(
            email,
            password,
            fakerName(),
            fakerName(),
            "0548876758",
            fakerPhoto(),
            fakerRole(),
            "", "", ""
        )
        fakerAddUser(user)
        return user
    }

    private fun fakerUserWithEmail(email: String) : User {

        val user = fakerUsers[email]
        if (user != null) return user

        return fakerUserWithEmailAndPassword(email, "$email password")
    }

    private fun fakerAddUser(user: User) {

        fakerUsers.put(user.email, user)
    }

    private fun fakerFactory() {

        for (i in 1..3) {
            fakerAddUser(fakerUser())
        }
    }

    fun fakerRegister(requestBody: RegRequestBody): LiveData<Boolean> {

        val user = requestBody as User
        fakerAddUser(user)
        LoggedInUser.user = user
        LoggedInUser.token = "abcdefghijklmnopqrszuvwxyz1234567890"
        val isSuccess = MutableLiveData<Boolean>()
        isSuccess.value = true
        return isSuccess
    }

    fun fakerLogin(email: String, password: String): LiveData<Boolean> {

        LoggedInUser.user = fakerUserWithEmailAndPassword(email, password)
        LoggedInUser.token = "abcdefghijklmnopqrszuvwxyz1234567890"
        val isSuccess = MutableLiveData<Boolean>()
        isSuccess.value = true
        return isSuccess
    }

    fun fakerProfile(email: String): LiveData<User> {

        val user = MutableLiveData<User>()
        user.value = fakerUserWithEmail(email)
        return user
    }
}
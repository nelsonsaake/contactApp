package com.ampersand.contactapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactApiService {

//    @Headers("{"
//        Content-Type: application/json,
//        Accept: application/json
//    }")
    @POST("/login")
    fun login(@Body logInRequest: LogInRequestBody) : Call<LogInResponse>

//    @Headers({
//        "Content-Type: application/json",
//        "Accept: application/json"
//    })
    @POST("/register")
    fun register(@Body regBody: RegRequestBody) : Call<RegResponse>

    companion object{

        fun create(): ContactApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ampersand-contact-exchange-api.herokuapp.com/api/v1/")
                .build()

            return retrofit.create(ContactApiService::class.java)
        }
    }
}

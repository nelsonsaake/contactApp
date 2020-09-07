package com.ampersand.contactapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST


interface LoginApiService {

    @POST("/api/v1/login")
    fun login(
       @Field("email") email: String,
       @Field("password") password: String
    ) : Call<LoginResponse>

    companion object {
        fun create(): LoginApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ampersand-contact-exchange-api.herokuapp.com")
                .build()

            return retrofit.create(LoginApiService::class.java)
        }
    }
}
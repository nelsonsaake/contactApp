package com.ampersand.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        maskPassword()
    }

    private fun maskPassword() {
        editTextTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
    }
}
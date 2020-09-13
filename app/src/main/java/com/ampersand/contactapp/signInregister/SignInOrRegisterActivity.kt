package com.ampersand.contactapp.signInregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ampersand.contactapp.R
import com.ampersand.contactapp.signInregister.register.RegActivity
import com.ampersand.contactapp.signInregister.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_in_or_register.*

class SignInOrRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_or_register)

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegActivity::class.java))
        }
    }
}
package com.ampersand.contactapp.signInregister.signin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactViewModel
import com.ampersand.contactapp.datasource.LOG_TAG
import com.ampersand.contactapp.exchangecontanct.ContactDisplayActivity
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel
    private lateinit var loggingInAnimationDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViewModel()
        setupCustomToolbar()
        maskPassword()
        onSignInButtonClicked()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private fun onSignInButtonClicked() {

        signInButton.setOnClickListener {

            signIn()
        }
    }

    private fun maskPassword() {

        passwordEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        // show password char
        // editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setupCustomToolbar() {

        supportActionBar?.apply {

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_center_title_and_back_button)
            customView.apply {

                findViewById<ImageView>(R.id.backButton)?.setOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun signIn() {

        clearClientSideError()

        // if email is valid
        if (!isEmailValid()) return

        // if password is valid
        if (!isPasswordValid()) return

        // sign in
        showAnimation()

        viewModel
            .login(emailEdit.text.toString(), passwordEdit.text.toString())
            .observe(this, Observer { isLoggedIn ->

                stopAnimation()
                if (isLoggedIn) {

                    Log.i(LOG_TAG, "starting the contact exchange")
                    startActivity(Intent(this, ContactDisplayActivity::class.java))
                }
            })

        listenForServerErrors()
    }

    private fun showAnimation() {

        loggingInAnimationDialog = ProgressDialog.show(
            this, "",
            "Logging in...", true
        )
    }

    private fun stopAnimation() {

        loggingInAnimationDialog.cancel()
    }

    private fun isEmailValid(): Boolean {

        val email = emailEdit.text.toString().toLowerCase()
        if (email.isEmpty()) {

            showClientSideError("please provide email")
            return false
        }

        if (!(email.endsWith("@ampersandllc.co") or email.endsWith("@gmail.com"))) {

            showClientSideError("email provided is not accepted; email: $email")
            return false
        }

        return true
    }

    private fun isPasswordValid(): Boolean {

        val password = passwordEdit.text.toString()
        var isPasswordValid = (password.length > 5) as Boolean

        if (!isPasswordValid) showClientSideError("password provided is too short")
        return isPasswordValid
    }

    private fun showClientSideError(msg: String) {

        errorText.text = msg
    }

    private fun clearClientSideError() {

        errorText.text = ""
    }

    private fun listenForServerErrors() {

        viewModel.loginError.observe(this, Observer { err ->

            showServerSideError(err)
        })
    }

    private fun showServerSideError(err: String) {

        // maybe in the future i will want to use this "err"

        AlertDialog.Builder(this)
            .setTitle("Login Error")
            .setMessage("An error occurred during your login. Please try again in a few moments")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Try Again", null)
            .show()
    }

}
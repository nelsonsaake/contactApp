package com.ampersand.contactapp

import android.content.DialogInterface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: LogInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViewModel()
        setupCustomToolbar()
        maskPassword()
        listenToSignInButton()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
    }

    private fun listenToSignInButton() {

        signInButton.setOnClickListener {
            signIn()
            
            // at this point I will just ignore the sign in and just move to the next page
            // definitely todo
            startActivity(Intent(this, ExchangeContactActivity::class.java))
        }
    }

    private fun maskPassword() {

        passwordEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        // show password char
        // editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setupCustomToolbar() {

        supportActionBar?.apply{

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_center_title_and_back_button)
            customView.apply {

                findViewById<ImageView>(R.id.backButton)?.setOnClickListener {
                    finish()
                }
            }
        }
    }

    fun signIn() {

        clearClientSideError()

        // if email is valid
        val isEmailValid = validateEmail()

        // if password is valid
        // if both are invalid, one will the password error will be prioritized
        val isPassword = validatePassword()

        // sign
        if (isEmailValid && isPassword) {
            viewModel.login(emailEdit.text.toString(), passwordEdit.text.toString())
        }
    }

    private fun validateEmail(): Boolean {

        // make sure email ends with "@ampersandllc.co" aka ACCEPTED_EMAIL_DOMAIN
        val email = emailEdit.text.toString().toLowerCase()
        var isEmailValid = email.endsWith(ACCEPTED_EMAIL_DOMAIN)

        if (!isEmailValid) showClientSideError("email provided is not an ampersand email: $email")

        return isEmailValid
    }

    private fun validatePassword(): Boolean {

        val password = passwordEdit.text.toString()
        var isPasswordValid = (password.length < 8) as Boolean

        if (!isPasswordValid) showClientSideError("password provided is too short")

        return isPasswordValid
    }

    private fun showClientSideError(msg: String) {

        errorText.text = msg
    }

    private fun clearClientSideError() {

        errorText.text = ""
    }

    private fun showServerSideError(err: String) {

        AlertDialog.Builder(this)
            .setTitle("Login Error")
            .setMessage("An error occurred during your login. Please try again in a few moments")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Try Again", DialogInterface.OnClickListener { _, _ -> signIn() })
            /*.setIcon()*/
            .show()
    }

}
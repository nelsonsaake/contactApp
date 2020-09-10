package com.ampersand.contactapp

import android.content.DialogInterface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private val viewModel : LogInViewModel

    init {

        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setCustomToolbar()
        setBackNavigation()
        maskPassword()
        listenToSignInButton()
    }

    private fun listenToSignInButton(){

        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun maskPassword() {

        passwordEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        // show password char
        // editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setCustomToolbar() {

        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.toolbar_sign_in)
    }

    private fun setBackNavigation() {

        val toolbar = R.id.customToolbar as Toolbar
        setSupportActionBar(toolbar)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

        var isEmailValid = true

        // make sure email ends with "@ampersandllc.co" aka ACCEPTED_EMAIL_DOMAIN
        val email = emailEdit.text.toString().toLowerCase()
        isEmailValid = email.endsWith(ACCEPTED_EMAIL_DOMAIN)

        if (!isEmailValid) showClientSideError("email provided is not an ampersand email: $email")

        return isEmailValid
    }

    private fun validatePassword(): Boolean {

        var isPasswordValid = true

        val password = passwordEdit.text.toString()
        isPasswordValid = (password.length < 8) as Boolean

        if(!isPasswordValid) showClientSideError("password provided is too short")

        return isPasswordValid
    }

    private fun showClientSideError(msg: String) {

        errorText.text = msg
    }

    private fun clearClientSideError(){

        errorText.text = ""
    }

    private fun showServerSideError(err: String){

        AlertDialog.Builder(this)
            .setTitle("Login Error")
            .setMessage("An error occurred during your login. Please try again in a few moments")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Try Again", DialogInterface.OnClickListener { arg0, arg1 -> signIn()})
           /*.setIcon()*/
            .show()
    }

}
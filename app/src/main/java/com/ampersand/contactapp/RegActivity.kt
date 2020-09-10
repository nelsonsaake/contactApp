package com.ampersand.contactapp

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_reg.*

class RegActivity : AppCompatActivity() {

    private val addPhotoFragment = AddPhotoFragment()
    private val editPhotoFragment = EditPhotoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        setCustomToolbar()
        setBackNavigation()
        maskPassword()
        showAddPhotoFragment()
    }

    private fun maskPassword() {

        regPasswordEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        // show password char
        // editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setCustomToolbar() {

        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.toolbar_sign_in)
        getSupportActionBar()
            ?.customView
            ?.findViewById<TextView>(R.id.customToolBarTitleText)
            ?.text = "Register"
    }

    private fun setBackNavigation() {

        val toolbar = R.id.customToolbar as Toolbar
        setSupportActionBar(toolbar)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showAddPhotoFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.landingPageFragmentContainer, addPhotoFragment)
            .commitNow()
    }

    private fun showEditPhotoFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.landingPageFragmentContainer, editPhotoFragment)
            .commitNow()
    }

}
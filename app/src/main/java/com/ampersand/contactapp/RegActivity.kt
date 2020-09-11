package com.ampersand.contactapp

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reg.*

class RegActivity : AppCompatActivity() {

    private val addPhotoFragment = AddPhotoFragment()
    private val editPhotoFragment = EditPhotoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        setupCustomToolbar()
        maskPassword()
        showAddPhotoFragment()
    }

    private fun maskPassword() {

        regPasswordEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        // show password char
        // editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setupCustomToolbar() {

        supportActionBar?.apply {

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_center_title_and_back_button)
            customView.apply {

                findViewById<TextView>(R.id.customToolbarTitleText).text = "Register"
                findViewById<ImageView>(R.id.backButton).setOnClickListener {
                    finish()
                }
            }
        }
    }


    private fun showAddPhotoFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.regPhotoContainer, addPhotoFragment)
            .commitNow()
    }

    private fun showEditPhotoFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.regPhotoContainer, editPhotoFragment)
            .commitNow()
    }

}
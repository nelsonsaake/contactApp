package com.ampersand.contactapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupCustomToolbar()
    }


    private fun setupCustomToolbar() {

        supportActionBar?.apply {

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_center_title_and_back_button)
            customView.apply {

                findViewById<TextView>(R.id.customToolbarTitleText).text = "Member Profile"
                findViewById<ImageView>(R.id.backButton).setOnClickListener {
                    finish()
                }
            }
        }
    }

}
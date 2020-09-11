package com.ampersand.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class ExchangeContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_contact)

        setupCustomToolbar()
    }

    private fun setupCustomToolbar(){

        supportActionBar?.apply{

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_white_logo_and_square)
        }
    }
}
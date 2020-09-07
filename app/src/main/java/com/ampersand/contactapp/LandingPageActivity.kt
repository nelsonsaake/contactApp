package com.ampersand.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LandingPageActivity : AppCompatActivity() {

    private val nameFragment = LandingPageNameFragment()
    private val introFragment = LandingPageIntroFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        showNameFragment()

    }

    private fun showNameFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.landingPageFragmentContainer, nameFragment)
            .commitNow()
    }

    private fun showIntroFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.landingPageFragmentContainer, introFragment)
            .commitNow()
    }
}
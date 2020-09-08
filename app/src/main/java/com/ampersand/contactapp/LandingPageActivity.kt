package com.ampersand.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_landing_page_intro.*
import kotlinx.android.synthetic.main.fragment_landing_page_name.*

class LandingPageActivity : AppCompatActivity() {

    private val nameFragment = LandingPageNameFragment()
    private val introFragment = LandingPageIntroFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        showNameFragment()
        logo.setOnClickListener {
            showIntroFragment()
        }

        getStartedTextView.setOnClickListener {
            startSignInOrRegisterActivity()
        }
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

    private fun startSignInOrRegisterActivity(){

        startActivity(Intent(this, SignInOrRegisterActivity::class.java))
    }
}

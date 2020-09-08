package com.ampersand.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_landing_page.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_landing_page_intro.*
import kotlinx.android.synthetic.main.fragment_landing_page_name.*

class LandingPageActivity : AppCompatActivity() {

    private val nameFragment = LandingPageNameFragment()
    private val introFragment = LandingPageIntroFragment()
    private var isShowingNameFragment : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        showNameFragment()
        landingPageActivity.setOnClickListener {
          toggleFragment()
        }
    }

    private fun showNameFragment() {

        isShowingNameFragment = true
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.landingPageFragmentContainer, nameFragment)
            .commitNow()
    }

    private fun showIntroFragment() {

        isShowingNameFragment = false
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.landingPageFragmentContainer, introFragment)
            .commitNow()
    }

    private fun startSignInOrRegisterActivity(){

        startActivity(Intent(this, SignInOrRegisterActivity::class.java))
    }

    private fun toggleFragment(){

        if(isShowingNameFragment == null){
            showNameFragment()
        }else{
            if(isShowingNameFragment!!) showIntroFragment()
            else startSignInOrRegisterActivity()
        }
    }
}

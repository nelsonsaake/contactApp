package com.ampersand.contactapp.landingpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ampersand.contactapp.R
import com.ampersand.contactapp.signInregister.SignInOrRegisterActivity
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPageActivity : AppCompatActivity() {

    private val nameFragment =
        LandingPageNameFragment()
    private val introFragment =
        LandingPageIntroFragment()
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

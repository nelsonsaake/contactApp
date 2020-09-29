package com.ampersand.contactapp.profile

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactViewModel
import com.ampersand.contactapp.datasource.EMAIL_INTENT_EXTRA
import com.ampersand.contactapp.datasource.LOG_TAG

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModel: ContactViewModel
    val profileLoadingFragment =
        ProfileLoadingFragment()
    val profileLoadedFragment =
        ProfileLoadedFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initViewModel()
        setupCustomToolbar()
        showLoadingFragment()
        loadProfile(intent.getStringExtra(EMAIL_INTENT_EXTRA) ?: "")
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private fun loadProfile(email: String) {

        if(email == "") {
            Log.e(LOG_TAG, "Email provided to load profile is empty: PofileActivity::loadProfile()")
            Toast.makeText(this, "Bad profile", Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.profile(email).observe(this, Observer { user ->

            profileLoadedFragment.displayProfile(user)
            showLoadedFragment()
        })

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

    private fun showLoadingFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.profileFragmentContainer, profileLoadingFragment)
            .commit()
    }

    private fun showLoadedFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.profileFragmentContainer, profileLoadedFragment)
            .commit()
    }
}
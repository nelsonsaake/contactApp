package com.ampersand.contactapp.profile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.datasource.ContactApiViewModel
import com.ampersand.contactapp.R

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModel : ContactApiViewModel
    val profileLoadingFragment =
        ProfileLoadingFragment()
    val  profileLoadedFragment =
        ProfileLoadedFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initViewModel()
        setupCustomToolbar()
        showLoadingFragment()
        loadProfile()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactApiViewModel::class.java)
    }

    private fun loadProfile() {

        /*
         *  here we get the intent extra, identifying user
         *  decode it and display it
         *
            viewModel.getProfile().observe(this, Observer{

                profileLoadedFragment.displayUser()
                showLoadedFragment()
            })
        */
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

    private fun showLoadingFragment(){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.profileFragmentContainer, profileLoadingFragment)
            .commit()
    }

    private fun showLoadedFragment(){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.profileFragmentContainer, profileLoadedFragment)
            .commit()
    }
}
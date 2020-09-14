package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.ampersand.contactapp.profile.ProfileActivity
import com.ampersand.contactapp.R
import kotlinx.android.synthetic.main.activity_contact_display.*

class ContactDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_display)

        setupCustomToolbar()
        onScanQRClicked()
        onProfileClicked()
    }

    private fun onProfileClicked() {

        ecMemberProfile.setOnClickListener {

            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun onScanQRClicked() {

        scanQRButton.setOnClickListener {

            startActivity(Intent(this, ContactScannerActivity::class.java))
        }
    }

    private fun setupCustomToolbar(){

        supportActionBar?.apply{

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_white_logo_and_square)
        }
    }
}
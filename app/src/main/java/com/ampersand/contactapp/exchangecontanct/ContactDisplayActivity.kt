package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactApiViewModel
import com.ampersand.contactapp.datasource.EMAIL_INTENT_EXTRA
import com.ampersand.contactapp.datasource.LOG_TAG
import com.ampersand.contactapp.datasource.LoggedInUser
import com.ampersand.contactapp.profile.ProfileActivity
import com.google.zxing.WriterException
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_display.*


class ContactDisplayActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_display)

        initViewModel()
        setupCustomToolbar()
        displayQRCode()
        displayLoggedInUserProfile()
        onScanQRClicked()
        onProfileClicked()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactApiViewModel::class.java)
    }

    private fun setupCustomToolbar() {

        supportActionBar?.apply {

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_white_logo_and_square)
        }
    }

    private fun displayQRCode() {

        val code = viewModel.userCode()
        var qrgEncoder = QRGEncoder(code, null, QRGContents.Type.TEXT, -1)
        try {
            var bitmap = qrgEncoder.encodeAsBitmap()
            qrCodeImage.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v(LOG_TAG, e.toString())
        }
    }

    private fun displayLoggedInUserProfile() {

        viewModel.getProfile(LoggedInUser.user?.email!!).observe(this, Observer { profile ->

            Picasso
                .with(this)
                .load(profile.photo)
                .into(ecProfileImage)

            ecNameText.text = "${profile.firstName} ${profile.lastName}"

            ecRoleText.text = profile.role
        })
    }

    private fun onScanQRClicked() {

        scanQRButton.setOnClickListener {

            startActivity(Intent(this, ContactScannerActivity::class.java))
        }
    }

    private fun onProfileClicked() {

        ecMemberProfile.setOnClickListener {

            if (LoggedInUser.user != null) {

                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(EMAIL_INTENT_EXTRA, LoggedInUser.user?.email)
                startActivity(intent)
            }
        }
    }

}
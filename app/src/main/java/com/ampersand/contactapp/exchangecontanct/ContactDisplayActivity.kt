package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactViewModel
import com.ampersand.contactapp.datasource.EMAIL_INTENT_EXTRA
import com.ampersand.contactapp.datasource.LoggedInUser
import com.ampersand.contactapp.profile.ProfileActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_display.*


class ContactDisplayActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel

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

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private fun setupCustomToolbar() {

        supportActionBar?.apply {

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_white_logo_and_square)
        }
    }

    private fun displayQRCode() {

        val text: String = viewModel.userCode()
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qrCodeImage.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun displayLoggedInUserProfile() {

        viewModel.profile(LoggedInUser.user?.email!!).observe(this, Observer { profile ->

            Picasso
                .with(this)
                .load(profile.photo)
                .fit()
                .centerCrop()
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
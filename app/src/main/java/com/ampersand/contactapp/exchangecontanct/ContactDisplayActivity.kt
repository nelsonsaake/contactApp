package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactApiViewModel
import com.ampersand.contactapp.datasource.USER_CODE_EXTRA
import com.ampersand.contactapp.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_contact_display.*

class ContactDisplayActivity : AppCompatActivity() {

    var userCode: String? = null

    lateinit var viewModel: ContactApiViewModel

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

        // this is a small sample use of the QRCodeEncoder class from zxing
        // generate a 150x150 QR code
        viewModel.generateQRCodeForCurrentUser().observe(this, Observer { code ->

            /*
                Bitmap bm = encodeAsBitmap (code, BarcodeFormat.QR_CODE, 150, 150);
                if (bm != null) {
                    qrCodeImage.setImageBitmap(bm);
                }
             */
            TODO()
        })
    }

    private fun displayLoggedInUserProfile() {

        TODO()
    }

    private fun onScanQRClicked() {

        scanQRButton.setOnClickListener {

            startActivity(Intent(this, ContactScannerActivity::class.java))
        }
    }

    private fun onProfileClicked() {

        ecMemberProfile.setOnClickListener {

            if (userCode != null) {

                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(USER_CODE_EXTRA, userCode)
                startActivity(intent)
            }
        }
    }

}
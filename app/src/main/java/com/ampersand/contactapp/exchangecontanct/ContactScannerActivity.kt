package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactApiViewModel
import com.ampersand.contactapp.datasource.EMAIL_INTENT_EXTRA
import com.ampersand.contactapp.profile.ProfileActivity
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_contact_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ContactScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var scannerView: ZXingScannerView? = null

    private lateinit var viewModel: ContactApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_scanner)

        initViewModel()
        onSendQRClicked()
        setupScanner()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactApiViewModel::class.java)
    }

    private fun onSendQRClicked() {

        sendQRButton.setOnClickListener {

            startActivity(Intent(this, ContactScannerActivity::class.java))
        }
    }

    private fun setupScanner() {

        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

        scannerContainer.addView(scannerView)
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    public override fun onDestroy() {

        super.onDestroy()
        scannerView?.stopCamera()
    }

    override fun handleResult(result: Result) {

        val userCode = result.text
        Toast.makeText(this, userCode, Toast.LENGTH_SHORT).show()
        saveContactInPhoneBook(userCode)
        showProfile(userCode)
    }

    private fun saveContactInPhoneBook(userCode: String) {

        viewModel.addContact(this, userCode)
    }

    private fun showProfile(userCode: String) {

        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(EMAIL_INTENT_EXTRA, viewModel.decode(userCode))
        startActivity(intent)
    }
}


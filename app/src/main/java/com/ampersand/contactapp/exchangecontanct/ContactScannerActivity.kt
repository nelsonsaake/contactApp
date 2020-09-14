package com.ampersand.contactapp.exchangecontanct

import android.content.ContentProviderOperation
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.ampersand.contactapp.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_contact_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ContactScannerActivity : AppCompatActivity(), ResultHandler {

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
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    public override fun onDestroy() {

        super.onDestroy()
        scannerView.stopCamera()
    }

    private override fun handleResult(result: Result) {

        val userCode = result.getText()
        Toast.makeText(getApplicationContext(), userCode, Toast.LENGTH_SHORT).show()
        saveContactInPhoneBook(userCode)
        showProfile(userCode)
    }

    private fun saveContactInPhoneBook(userCode: String) {

       viewModel.addContact(userCode)
    }

    private fun showProfile(userCode: String) {

        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(USER_CODE_EXTRA, userCode)
        startActivity(intent)
    }
}


package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactViewModel
import com.ampersand.contactapp.datasource.EMAIL_INTENT_EXTRA
import com.ampersand.contactapp.profile.ProfileActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import kotlinx.android.synthetic.main.activity_contact_scanner.*

class ContactScannerActivity : AppCompatActivity(){

    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_scanner)

        initViewModel()
        onSendQRClicked()
        setupScanner()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private fun onSendQRClicked() {

        sendQRButton.setOnClickListener {

           finish()
        }
    }

    private fun setupScanner() {

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        // or CAMERA_FRONT or specific camera id
        codeScanner.camera = CodeScanner.CAMERA_BACK

        // list of type BarcodeFormat,
        codeScanner.formats = CodeScanner.ALL_FORMATS

        // ex. listOf(BarcodeFormat.QR_CODE)
        // or CONTINUOUS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE

        // or CONTINUOUS or PREVIEW
        codeScanner.scanMode = ScanMode.SINGLE

        // Whether to enable auto focus or not
        codeScanner.isAutoFocusEnabled = true

        // Whether to enable flash or not
        codeScanner.isFlashEnabled = false

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                handleResult(it.text)
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    fun handleResult(result: String) {

        val userCode = result
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


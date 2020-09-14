package com.ampersand.contactapp.exchangecontanct

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.ampersand.contactapp.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_contact_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ContactScannerActivity : AppCompatActivity(), ResultHandler {

    private var scannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_scanner)

        onSendQRClicked()
        setupScanner()
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

    override fun handleResult(result: Result) {

        val myResult: String = result.getText()
    }
}
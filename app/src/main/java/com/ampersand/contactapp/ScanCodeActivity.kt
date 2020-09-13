package com.ampersand.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exchange_contact.*
import kotlinx.android.synthetic.main.activity_scan_code.*

class ScanCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)

        onSendQRClicked()
    }

    private fun onSendQRClicked() {

        sendQRButton.setOnClickListener {

            startActivity(Intent(this, ScanCodeActivity::class.java))
        }
    }
}
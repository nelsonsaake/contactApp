package com.ampersand.contactapp.signInregister.register

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactApiViewModel
import com.ampersand.contactapp.datasource.PICK_IMAGE_REQUEST_CODE
import com.ampersand.contactapp.exchangecontanct.ContactDisplayActivity
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody
import com.filestack.Config
import com.filestack.FileLink
import com.filestack.android.FsActivity
import com.filestack.android.FsConstants
import kotlinx.android.synthetic.main.activity_reg.*


class RegActivity : AppCompatActivity() {

    private val addPhotoFragment =
        RegAddPhotoFragment()
    private val editPhotoFragment =
        RegEditPhotoFragment()
    private lateinit var viewModel: ContactApiViewModel
    private var uploadReceiver: UploadReceiver? = null
    var fileLink: FileLink? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        initViewModel()
        setupCustomToolbar()
        maskPassword()
        showAddPhotoFragment()
        onRegisterButtonClicked()
        onPhotoButtonsClicked()
        setupFileStack()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactApiViewModel::class.java)
    }

    private fun maskPassword() {

        regPasswordEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        // show password char
        // editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setupCustomToolbar() {

        supportActionBar?.apply {

            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.toolbar_center_title_and_back_button)
            customView.apply {

                findViewById<TextView>(R.id.customToolbarTitleText).text = "Register"
                findViewById<ImageView>(R.id.backButton).setOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun showAddPhotoFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.regPhotoContainer, addPhotoFragment)
            .commitNow()
    }

    private fun showEditPhotoFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.regPhotoContainer, editPhotoFragment)
            .commitNow()
    }

    private fun onRegisterButtonClicked() {

        regButton.setOnClickListener {

            listenForServerErrors()

            var photo = ""
            photo = fileLink!!.handle

            val regRequestBody = RegRequestBody(
                regEmailEdit.str(),
                regPasswordEdit.str(),
                regFirstNameEdit.str(),
                regLastNameEdit.str(),
                photo,
                regRoleEdit.str(),
                regPhoneEdit.str(),
                regTwitterEdit.str(),
                regLinkedInEdit.str(),
                regWebsiteEdit.str()
            )

            /*
             * naturally I will observe the register response to let user in
             * but since the api is not working, i will just let the user in
            */
            viewModel.register(regRequestBody).observe(this, Observer { isRegistered ->

                if (isRegistered) {
                    startActivity(Intent(this, ContactDisplayActivity::class.java))
                }
            })
        }
    }

    private fun listenForServerErrors() {

        viewModel.regError.observe(this, Observer { err ->

            showServerSideError(err)
        })
    }

    private fun showServerSideError(err: String) {

        AlertDialog.Builder(this)
            .setTitle("Register Error")
            .setMessage(err)
            .setPositiveButton("Ok", null)
            /*.setIcon()*/
            .show()
    }

    private fun onPhotoButtonsClicked() {

        onAddPhotoButtonClicked()
        onEditPhotoButtonClicked()
    }

    private fun onAddPhotoButtonClicked() {

        addPhotoFragment.addPhotoButton.setOnClickListener {

            selectPhoto()
            showEditPhotoFragment()
        }
    }

    private fun onEditPhotoButtonClicked() {

        editPhotoFragment.editPhotoButton.setOnClickListener {

            selectPhoto()
        }
    }

    private fun selectPhoto() {

        val apiKey = getString(R.string.file_stack_api_key)
        val config = Config(apiKey, "https://form.samples.android.filestack.com")
        val pickerIntent = Intent(this, FsActivity::class.java)
        pickerIntent.putExtra(FsConstants.EXTRA_CONFIG, config)
        val mimeTypes = arrayOf("image/*")
        pickerIntent.putExtra(FsConstants.EXTRA_MIME_TYPES, mimeTypes)
        startActivity(pickerIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {


        }
    }

    private fun setImage(url: String) {

        // display image
        editPhotoFragment.setImage(url)
    }

    inner class UploadReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {

            fileLink = intent.getSerializableExtra(FsConstants.EXTRA_FILE_LINK) as FileLink
        }
    }

    fun setupFileStack() {

        // Register the receiver for upload broadcasts
        val filter = IntentFilter(FsConstants.BROADCAST_UPLOAD)
        uploadReceiver = UploadReceiver()
        LocalBroadcastManager.getInstance(this).registerReceiver(uploadReceiver!!, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver to avoid leaking it outside tne activity context
        LocalBroadcastManager.getInstance(this).unregisterReceiver(uploadReceiver!!)
    }

    override fun onResume() {
        super.onResume()

        if (fileLink != null) {
            val url: String = fileLink!!.getHandle()
            setImage(url)
        }
    }
}

private fun EditText.str(): String {

    return this.text.toString()
}

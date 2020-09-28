package com.ampersand.contactapp.signInregister.register

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactViewModel
import com.ampersand.contactapp.datasource.LOG_TAG
import com.ampersand.contactapp.datasource.PICK_IMAGE_REQUEST_CODE
import com.ampersand.contactapp.exchangecontanct.ContactDisplayActivity
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody
import kotlinx.android.synthetic.main.activity_reg.*
import kotlinx.android.synthetic.main.fragment_reg_page_add_photo.*
import kotlinx.android.synthetic.main.fragment_reg_page_edit_photo.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegActivity : AppCompatActivity(), View.OnClickListener {

    private val addPhotoFragment = RegAddPhotoFragment(this)
    private val editPhotoFragment = RegEditPhotoFragment(this)
    private lateinit var viewModel: ContactViewModel
    private var regPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        initViewModel()
        setupCustomToolbar()
        maskPassword()
        showAddPhotoFragment()
        regButton.setOnClickListener(this)
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
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

                findViewById<TextView>(R.id.customToolbarTitleText).text = context.getString(R.string.register_page_title)
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

    private fun register() {

        listenForServerErrors()

        val regRequestBody = RegRequestBody(
            regEmailEdit.str(),
            regPasswordEdit.str(),
            regFirstNameEdit.str(),
            regLastNameEdit.str(),
//            photoToString(),
            regPhotoUri.toString(),
            regRoleEdit.str(),
            regPhoneEdit.str(),
            regTwitterEdit.str(),
            regLinkedInEdit.str(),
            regWebsiteEdit.str()
        )

        // TODO
        // throw exception for the invalid fields for more detailed report to user
        if(!regRequestBody.isValid()) return;

        viewModel.register(regRequestBody).observe(this, Observer { isRegistered ->

            if (isRegistered) {
                startActivity(Intent(this, ContactDisplayActivity::class.java))
            }
        })
    }

    private fun listenForServerErrors() {

        viewModel.regError.observe(this, Observer { err ->

            showServerSideError(err)
        })
    }

    private fun showServerSideError(err: String) {

        if(err.isEmpty()) return

        AlertDialog.Builder(this)
            .setTitle("Register Error")
            .setMessage(err)
            .setPositiveButton("Ok", null)
            .show()
    }

    private fun selectPhoto() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST_CODE)
    }

    private fun displayPhoto(url: String) {

        // display image
        editPhotoFragment.setPhoto(url)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                Log.i(LOG_TAG, regPhotoUri.toString())
                regPhotoUri = data?.data
                displayPhoto(regPhotoUri.toString())
            }
        }
    }

    fun photoToString(): String{

        if(regPhotoUri == null){
            return ""
        }
        val inputStream = contentResolver.openInputStream(regPhotoUri!!)
        val bytes: ByteArray  = inputStream?.readBytes()!!
        inputStream.close()
        return String(bytes)
    }

    override fun onClick(v: View?) {

        when(v){
            addProfilePhotoText -> {
                selectPhoto()
                showEditPhotoFragment()
            }
            editProfilePhotoText -> {
                selectPhoto()
            }
            regButton -> {
                GlobalScope.launch{
                    regButton.isEnabled = false
                    val pd = showProgressDialog()
                    register()
                    closeProgressDialog(pd)
                    regButton.isEnabled = true
                }
            }
        }
    }

    private fun closeProgressDialog(pd: ProgressDialog) {
        pd.cancel()
    }

    private fun showProgressDialog(): ProgressDialog {
        return ProgressDialog.show(
            this, "",
            "Registering in...", true
        )
    }
}

private fun EditText.str(): String {

    return this.text.toString()
}


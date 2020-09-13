package com.ampersand.contactapp.signInregister.register

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.ContactApiViewModel
import com.ampersand.contactapp.exchangecontanct.ExchangeContactActivity
import com.ampersand.contactapp.signInregister.register.model.RegRequestBody
import kotlinx.android.synthetic.main.activity_reg.*


class RegActivity : AppCompatActivity() {

    private val addPhotoFragment =
        RegAddPhotoFragment()
    private val editPhotoFragment =
        RegEditPhotoFragment()
    private lateinit var viewModel: ContactApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        initViewModel()
        setupCustomToolbar()
        maskPassword()
        showAddPhotoFragment()
        onRegisterButtonClicked()
        onPhotoButtonsClicked()
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

        regButton.setOnClickListener{

            listenForServerErrors()

            val regRequestBody = RegRequestBody(
                regEmailEdit.str(),
                regPasswordEdit.str(),
                regFirstNameEdit.str(),
                regLastNameEdit.str(),
                regRoleEdit.str(),
                regPhoneEdit.str(),
                regTwitterEdit.str(),
                regLinkedInEdit.str(),
                regWebsiteEdit.str(),
                TODO()
            )
            viewModel.register(regRequestBody).observe(this, Observer { isRegistered ->

                if (isRegistered) {
                    startActivity(Intent(this, ExchangeContactActivity::class.java))
                }
            })
        }
    }

    private fun listenForServerErrors() {

        viewModel.regError.observe(this, Observer{ err ->

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

    private fun onPhotoButtonsClicked(){

        onAddPhotoButtonClicked()
        onEditPhotoButtonClicked()
    }

    private fun onAddPhotoButtonClicked(){

        addPhotoFragment.addPhotoButton.setOnClickListener{

            selectPhoto()
        }
    }

    private fun onEditPhotoButtonClicked(){

        editPhotoFragment.editPhotoButton.setOnClickListener{

            selectPhoto()
        }
    }

    private fun selectPhoto(){

        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_IMAGE)
    }
}

private fun EditText.str(): String {

    return this.text.toString()
}

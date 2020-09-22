package com.ampersand.contactapp.signInregister.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ampersand.contactapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_reg_page_edit_photo.*

class RegEditPhotoFragment : Fragment() {

    lateinit var editPhotoButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reg_page_edit_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editPhotoButton = editProfilePhotoText
    }

    fun setImage(url: String){

        Picasso
            .with(context)
            .load(url)
            .into(regProfilePhoto)
    }
}
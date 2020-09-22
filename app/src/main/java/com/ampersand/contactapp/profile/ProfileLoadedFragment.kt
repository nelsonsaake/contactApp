package com.ampersand.contactapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ampersand.contactapp.R
import com.ampersand.contactapp.datasource.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_display.*
import kotlinx.android.synthetic.main.fragment_profile_loaded.*

class ProfileLoadedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_loaded, container, false)
    }

    fun displayProfile(profile: User){


        Picasso
            .with(context)
            .load(profile.photo)
            .into(profilePhoto)

        profileNameText.text = "${profile.firstName} ${profile.lastName}"

        profileRoleText.text = profile.role

        TODO("finish setting values for the rest of the widgets")
    }
}
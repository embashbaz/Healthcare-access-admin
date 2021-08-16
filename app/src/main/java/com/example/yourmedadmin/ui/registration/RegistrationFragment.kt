package com.example.yourmedadmin.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import com.example.yourmedadmin.R
import com.google.android.material.textfield.TextInputLayout


class RegistrationFragment : Fragment() {

    lateinit var nameTl: TextInputLayout
    lateinit var phoneTl: TextInputLayout
    lateinit var emailTl: TextInputLayout
    lateinit var passwordTl: TextInputLayout
    lateinit var confirmPasswordTl: TextInputLayout
    lateinit var licenseTl: TextInputLayout
    lateinit var townTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var countrySp: Spinner
    lateinit var serviceOfferedSp: Spinner
    lateinit var coordinateTxt: TextView
    lateinit var uploadLicenseTxt: TextView
    lateinit var registerProgress: ProgressBar
    lateinit var registerBt: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    fun bindViews(view: View){
        nameTl = view.findViewById(R.id.name_register_tl)
        phoneTl = view.findViewById(R.id.phone_register_tl)
        emailTl = view.findViewById(R.id.email_register_tl)
        passwordTl = view.findViewById(R.id.password_register_tl)
        confirmPasswordTl = view.findViewById(R.id.confirm_password_register_tl)
        licenseTl = view.findViewById(R.id.licence_register_tl)
        townTl = view.findViewById(R.id.town_register_tl)
        detailTl = view.findViewById(R.id.details_register_tl)
        countrySp = view.findViewById(R.id.country__register_sp)
        serviceOfferedSp = view.findViewById(R.id.service_offered_register_sp)
        coordinateTxt = view.findViewById(R.id.coordinate_register_txt)
        uploadLicenseTxt = view.findViewById(R.id.upload_license_txt)
        registerProgress = view.findViewById(R.id.progressBar_register)
        registerBt = view.findViewById(R.id.register_bt)
    }
}
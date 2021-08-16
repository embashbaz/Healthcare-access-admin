package com.example.yourmedadmin.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.yourmedadmin.R
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    lateinit var emailTl: TextInputLayout
    lateinit var passwordTl: TextInputLayout
    lateinit var forgotPwdBt : Button
    lateinit var registerBt : Button
    lateinit var loginBt : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    fun bindViews(view: View){
        emailTl = view.findViewById(R.id.email_login)
        passwordTl = view.findViewById(R.id.password_login)
        forgotPwdBt = view.findViewById(R.id.forgot_password)
        registerBt = view.findViewById(R.id.register_login)
        loginBt = view.findViewById(R.id.login_button)

    }
}
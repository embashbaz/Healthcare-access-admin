package com.example.yourmedadmin.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.ui.dialogs.InfoDialog
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    lateinit var emailTl: TextInputLayout
    lateinit var passwordTl: TextInputLayout
    lateinit var forgotPwdBt : Button
    lateinit var registerBt : Button
    lateinit var loginBt : Button

    val loginViewModel : LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        bindViews(view)
        loginBt.setOnClickListener{
            loginMethod()
        }

        registerBt.setOnClickListener{
            this.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return view
    }

    fun bindViews(view: View){
        emailTl = view.findViewById(R.id.email_login)
        passwordTl = view.findViewById(R.id.password_login)
        forgotPwdBt = view.findViewById(R.id.forgot_password)
        registerBt = view.findViewById(R.id.register_login)
        loginBt = view.findViewById(R.id.login_button)

    }

    fun loginMethod(){
        if (!emailTl.editText?.text.isNullOrEmpty() && !passwordTl.editText?.text.isNullOrEmpty() ) {
            loginViewModel.signIn(
                emailTl.editText?.text.toString(),
                passwordTl.editText?.text.toString()
            )

            loginViewModel.loginOutput.observe(viewLifecycleOwner, {

                if (it.status == "success" && it.statusValue =="success") {
                    (activity?.application as HealthAccessAdmin).uId = it.careAdmin!!.uId.toString()
                    (activity?.application as HealthAccessAdmin).mCareAdmin = it.careAdmin!!
                  this.findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }else if (it.status == "failed"){
                   //TODO: show dialog
                    val dialog = it.statusValue?.let { it1 -> InfoDialog(it1) }
                    dialog?.show(parentFragmentManager, "Error logging in")
                }
            })
        }else{
            Toast.makeText(activity, "Please provide the email and password" , Toast.LENGTH_LONG)
                .show()
        }

    }
}
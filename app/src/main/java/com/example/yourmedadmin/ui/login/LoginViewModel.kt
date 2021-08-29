package com.example.yourmedadmin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.LoginCareAdminObject
import com.example.yourmedadmin.data.Repository

class LoginViewModel : ViewModel(){

    val repository = Repository()

    private var _loginOutput = MutableLiveData<LoginCareAdminObject>()
    val loginOutput: LiveData<LoginCareAdminObject>
        get() = _loginOutput

    fun signIn (email: String, password: String){
        _loginOutput = repository.login(email, password)
    }
}
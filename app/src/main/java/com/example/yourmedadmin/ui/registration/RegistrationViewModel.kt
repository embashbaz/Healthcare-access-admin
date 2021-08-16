package com.example.yourmedadmin.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.CareAdmin
import com.example.yourmedadmin.data.Repository

class RegistrationViewModel  : ViewModel(){
    val repository = Repository()


    private var _registrationOutput = MutableLiveData<HashMap<String, String>>()
    val registrationOutput: LiveData<HashMap<String, String>>
        get() = _registrationOutput

    fun signUp (medAdmin: CareAdmin, password: String){
        _registrationOutput = repository.register(medAdmin, password)
    }
}
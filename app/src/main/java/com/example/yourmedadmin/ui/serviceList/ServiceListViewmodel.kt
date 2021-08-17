package com.example.yourmedadmin.ui.serviceList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Repository
import com.example.yourmedadmin.data.Service

class ServiceListViewmodel : ViewModel() {
    val repository = Repository()

    fun getServices(uId: String): MutableLiveData<List<Service>> {
        return repository.getServices(uId)
    }
}
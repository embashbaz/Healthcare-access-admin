package com.example.yourmedadmin.ui.ServiceDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Repository
import com.example.yourmedadmin.data.Service

class ServiceDetailViewmodel : ViewModel() {
    val repository = Repository()


    private var _addingServiceOutput = MutableLiveData<HashMap<String, String>>()
    val addingServiceOutput: LiveData<HashMap<String, String>>
        get() = _addingServiceOutput

    private var _updatingServiceOutput = MutableLiveData<HashMap<String, String>>()
    val updatingServiceOutput: LiveData<HashMap<String, String>>
        get() = _updatingServiceOutput

    private var _deletingServiceOutput = MutableLiveData<HashMap<String, String>>()
    val deletingServiceOutput: LiveData<HashMap<String, String>>
        get() = _deletingServiceOutput



    fun updateService(uId: String, docId: String, service: Service){
        _updatingServiceOutput = repository.updateService(uId,docId, service)
    }

    fun deleteService(uId: String, docId: String){
        _deletingServiceOutput = repository.deleteService(uId, docId)
    }

    fun saveNewService(service: Service, uId: String){
        _addingServiceOutput = repository.saveNewService(service, uId)
    }
}
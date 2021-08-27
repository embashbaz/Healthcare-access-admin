package com.example.yourmedadmin.ui.indemandList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.InDemandQueryResults
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Repository

class IndemandListViewModel : ViewModel(){

    val repository = Repository()

    fun getproducts(country: String, county: String, serviceType: String ): MutableLiveData<InDemandQueryResults> {
        return repository.getIndemandList(country,county, serviceType)
    }

}
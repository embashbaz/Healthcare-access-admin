package com.example.yourmedadmin.ui.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Repository

class ProductListViewmodel  : ViewModel(){

    val repository = Repository()

    fun getproducts(uId: String): MutableLiveData<List<Medicine>>{
        return repository.getMedicines(uId)
    }



}
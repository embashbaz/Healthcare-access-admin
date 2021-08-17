package com.example.yourmedadmin.ui.productDetail

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Repository

class ProductDetailViewModel : ViewModel() {

    val repository = Repository()


    private var _addingProductOutput = MutableLiveData<HashMap<String, String>>()
    val addingProductOutput: LiveData<HashMap<String, String>>
        get() = _addingProductOutput

    private var _updatingProductOutput = MutableLiveData<HashMap<String, String>>()
    val updatingProductOutput: LiveData<HashMap<String, String>>
        get() = _updatingProductOutput

    private var _deletingProductOutput = MutableLiveData<HashMap<String, String>>()
    val deletingProductOutput: LiveData<HashMap<String, String>>
        get() = _deletingProductOutput



    fun updateProduct(uId: String, docId: String, medicine: Medicine){
        _updatingProductOutput = repository.updateMedicine(uId,docId, medicine)
    }

    fun deleteMedicine(uId: String, docId: String){
        _deletingProductOutput = repository.deleteMedicine(uId, docId)
    }

    fun saveNewProduct(medicine: Medicine, uId: String){
        _addingProductOutput = repository.saveNewProduct(medicine, uId)
    }
}
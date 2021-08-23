package com.example.yourmedadmin.ui.productDetail

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Repository
import com.example.yourmedadmin.data.RxtermApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

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

    private val _responsePrediction = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val responsePrediction: LiveData<String>
        get() = _responsePrediction



    fun updateProduct(uId: String, docId: String, medicine: Medicine){
        _updatingProductOutput = repository.updateMedicine(uId,docId, medicine)
    }

    fun deleteMedicine(uId: String, docId: String){
        _deletingProductOutput = repository.deleteMedicine(uId, docId)
    }

    fun saveNewProduct(medicine: Medicine, uId: String){
        _addingProductOutput = repository.saveNewProduct(medicine, uId)
    }

    fun getRecomendation(predText: String){
       RxtermApi.retrofitService.getTerms(predText).enqueue(object : Callback,
           retrofit2.Callback<String> {
           override fun onResponse(call: Call<String>, response: Response<String>) {
               _responsePrediction.value = response.body()
          val items = response.body()?.split(",[[\"", "\"],[\"","\"]]]")?.toMutableList()
            items?.removeFirst()
             for(item in items!!)
               Log.i("THISSSSSSSS", item)

           }

           override fun onFailure(call: Call<String>, t: Throwable) {
               _responsePrediction.value = "Failure: " + t.message
           }

       })

    }
}
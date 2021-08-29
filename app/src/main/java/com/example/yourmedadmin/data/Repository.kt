package com.example.yourmedadmin.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Repository {

    var mFirebaseAuth: FirebaseAuth
    var mFirebaseDb : FirebaseFirestore
   // var mFirebaseStore: FirebaseStorage

    init {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseDb = FirebaseFirestore.getInstance()
       // mFirebaseStore = FirebaseStorage.getInstance()

    }

    fun login(email: String, password: String): MutableLiveData<LoginCareAdminObject> {
        val operationOutput = MutableLiveData<LoginCareAdminObject>()
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                var data  = LoginCareAdminObject()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithEmail:success")

                    data.status = "success"

                    mFirebaseDb.collection("providers").document(mFirebaseAuth.uid.toString()).get()
                        .addOnSuccessListener {
                            if (it != null) {
                                data.status = "success"
                                data.statusValue = "success"
                                data.careAdmin = it.toObject(CareAdmin::class.java)

                            }else{
                                data.status = "failed"
                                data.statusValue = "You are not an admin and can't access this portal"
                            }
                            operationOutput.postValue(data)

                        }.addOnFailureListener{
                            data.status = "failed"
                            data.statusValue = it.toString()
                            operationOutput.postValue(data)
                        }


                } else {
                    data.status = "failed"
                    data.statusValue = task.exception.toString()

                }

                operationOutput.postValue(data)

            }


        return operationOutput
    }

    fun register(careAdmin: CareAdmin, password: String): MutableLiveData<HashMap<String, String>>{

        val operationOutput = MutableLiveData<HashMap<String, String>>()
        var status = hashMapOf<String, String>()
        mFirebaseAuth.createUserWithEmailAndPassword( careAdmin.email,  password)
            .addOnCompleteListener (){ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val userId = mFirebaseAuth.uid
                    if (userId != null) {
                        careAdmin.uId = userId.toString()
                    }

                    mFirebaseDb.collection("providers").document(userId.toString()).set(careAdmin)
                        .addOnSuccessListener {
                            Log.d(ContentValues.TAG, "DocumentSnapshot written")
                            status.put("status", "success")
                            status.put("value", userId.toString())
                            mFirebaseAuth.signOut()
                            operationOutput.postValue(status)

                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error adding document", e)
                            status.put("status", "failed")
                            status.put("value",
                                "Operation Failed with error"+ e.toString()
                            )
                            operationOutput.postValue(status)
                        }

                } else {
                    status.put("status", "failed")
                    status.put("value",
                        "Operation Failed with error"+ task.exception
                    )
                }
                operationOutput.postValue(status)

            }

        return operationOutput

    }

    fun saveNewService(service: Service, uId: String): MutableLiveData<HashMap<String, String>>{
        val operationOutput = MutableLiveData<HashMap<String, String>>()
        var status = hashMapOf<String, String>()
        mFirebaseDb.collection("providers").document(uId).collection("services").add(service)
                        .addOnSuccessListener {
                            Log.d(ContentValues.TAG, "DocumentSnapshot written")
                            status.put("status", "success")
                            status.put("value","Record added" )
                            operationOutput.postValue(status)

                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error adding document", e)
                            status.put("status", "failed")
                            status.put("value",e.toString() )
                            operationOutput.postValue(status)
                        }


        operationOutput.postValue(status)


        return operationOutput
    }

    fun saveNewProduct(medicine: Medicine, uId: String): MutableLiveData<HashMap<String, String>>{
        val operationOutput = MutableLiveData<HashMap<String, String>>()

        var status = hashMapOf<String, String>()
                    mFirebaseDb.collection("providers").document(uId).collection("medicines")
                        .add(medicine)
                        .addOnSuccessListener {
                            Log.d(ContentValues.TAG, "DocumentSnapshot written")
                            status.put("status", "success")
                            status.put("value", "Record added")
                            operationOutput.postValue(status)

                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error adding document", e)
                            status.put("status", "failed")
                            status.put("value", e.toString())
                            operationOutput.postValue(status)
                        }

        operationOutput.postValue(status)


        return operationOutput
    }

    fun getMedicines(uId:String): MutableLiveData<List<Medicine>>{

        val data = MutableLiveData<List<Medicine>>()

        val productRef = mFirebaseDb.collection("providers").document(uId).collection("medicines")

        productRef
            .get()
            .addOnSuccessListener {

                val dataList = ArrayList<Medicine>()
                for (snapshot in it){

                    val docObject = snapshot.toObject(Medicine::class.java)
                    docObject.medUid = snapshot.id

                    dataList.add(docObject)
                }
                data.value = dataList

            }.addOnFailureListener {
                data.value = null
            }

        return data
    }

    fun getServices(uId:String): MutableLiveData<List<Service>>{

        val data = MutableLiveData<List<Service>>()

        val productRef = mFirebaseDb.collection("providers").document(uId).collection("services")

        productRef
            .get()
            .addOnSuccessListener {

                val dataList = ArrayList<Service>()
                for (snapshot in it){

                    val docObject = snapshot.toObject(Service::class.java)
                    docObject.serviceId = snapshot.id

                    dataList.add(docObject)
                }
                data.value = dataList

            }.addOnFailureListener {
                data.value = null
            }

        return data
    }

    fun updateService(uId: String, docId: String, service: Service): MutableLiveData<HashMap<String, String>>{
        val operationOutput = MutableLiveData<HashMap<String, String>>()
        var status = hashMapOf<String, String>()
        mFirebaseDb.collection("providers").document(uId).collection("services").document(docId).set(service)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot written")
                status.put("status", "success")
                status.put("value","Record added" )
                operationOutput.postValue(status)

            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                status.put("status", "failed")
                status.put("value",e.toString() )
                operationOutput.postValue(status)
            }



        return operationOutput
    }

    fun deleteMedicine(uId: String, docId: String): MutableLiveData<HashMap<String, String>>{

        val operationOutput = MutableLiveData<HashMap<String, String>>()
        var status = hashMapOf<String, String>()
        mFirebaseDb.collection("providers").document(uId).collection("medicines").document(docId).delete()
            .addOnSuccessListener {

                status.put("status", "success")
                status.put("value","Record deleted" )
                operationOutput.postValue(status)

            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                status.put("status", "failed")
                status.put("value",e.toString() )
                operationOutput.postValue(status)

            }



        return operationOutput
    }

    fun updateMedicine(uId: String, docId: String, medicine: Medicine): MutableLiveData<HashMap<String, String>>{
        val operationOutput = MutableLiveData<HashMap<String, String>>()
        var status = hashMapOf<String, String>()


        mFirebaseDb.collection("providers").document(uId).collection("medicines").document(docId).set(medicine)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot written")
                status.put("status", "success")
                status.put("value","Record added" )
                operationOutput.postValue(status)

            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                status.put("status", "failed")
                status.put("value",e.toString() )
                operationOutput.postValue(status)
            }



        return operationOutput
    }

    fun deleteService(uId: String, docId: String): MutableLiveData<HashMap<String, String>>{

        val operationOutput = MutableLiveData<HashMap<String, String>>()
        var status = hashMapOf<String, String>()
        mFirebaseDb.collection("providers").document(uId).collection("services").document(docId).delete()
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot written")
                status.put("status", "success")
                status.put("value","Record deleted" )
                operationOutput.postValue(status)

            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                status.put("status", "failed")
                status.put("value",e.toString() )
                operationOutput.postValue(status)
            }



        return operationOutput
    }

    fun getIndemandList(country: String, county: String, serviceType: String): MutableLiveData<InDemandQueryResults>{
        var queryResults = MutableLiveData<InDemandQueryResults>()
        var myResults = InDemandQueryResults()
        val serviceRef = mFirebaseDb.collection(serviceType)

        serviceRef.whereEqualTo("countryName", country)
            .whereEqualTo("countyName", county)
            .get()
            .addOnSuccessListener {

                if(!it.isEmpty) {
                    myResults.status = "success"
                    myResults.statusValue = "success"
                    for (snapshot in it) {
                        val service = snapshot.toObject(InDemand::class.java)
                        myResults.inDemadList.add(service)

                    }
                }else{
                    myResults.status = "success"
                    myResults.statusValue = "failed"
                }
                queryResults.postValue(myResults)
            }.addOnFailureListener{
                myResults.status = "failed"
                myResults.statusValue = it.toString()

                queryResults.postValue(myResults)
            }

        return queryResults
    }





}
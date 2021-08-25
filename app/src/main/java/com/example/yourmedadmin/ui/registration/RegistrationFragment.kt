package com.example.yourmedadmin.ui.registration

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.CareAdmin
import com.example.yourmedadmin.data.PredictionAutoComplete
import com.example.yourmedadmin.ui.dialogs.InfoDialog
import com.example.yourmedadmin.ui.login.LoginViewModel
import com.example.yourmedadmin.ui.other.PredictionAdapter
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.GeoPoint


class RegistrationFragment : Fragment(),CoordinateDialog.CoordinateDialogListener ,AdapterView.OnItemSelectedListener {

    lateinit var nameTl: TextInputLayout
    lateinit var phoneTl: TextInputLayout
    lateinit var emailTl: TextInputLayout
    lateinit var passwordTl: TextInputLayout
    lateinit var confirmPasswordTl: TextInputLayout
    lateinit var licenseTl: TextInputLayout
    lateinit var townTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var countrySp: Spinner
    lateinit var serviceOfferedSp: Spinner
    lateinit var coordinateTxt: TextView
    lateinit var uploadLicenseTxt: TextView
    lateinit var registerProgress: ProgressBar
    lateinit var registerBt: Button
    private lateinit var viewAnimator: ViewAnimator
    private val handler = Handler()
    lateinit var recyclerView: RecyclerView

    lateinit var name: String
    lateinit var phone: String
    lateinit var email: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var license: String
    lateinit var town: String
    lateinit var detail: String
    lateinit var country: String
    lateinit var serviceOffered: String
    var coordinate: GeoPoint? = null
    var uploadLicense = false
    private lateinit var placesClient: PlacesClient
    private var sessionToken: AutocompleteSessionToken? = null

    val registrationViewModel : RegistrationViewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    private val adapter =  PredictionAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        bindViews(view)

        if (activity?.application != null) {
            Places.initialize(activity?.application!!, getString(R.string.google_maps_key))
        }

        // Create a new PlacesClient instance
        placesClient = activity?.let { Places.createClient(it) }!!

        registerBt.setOnClickListener{
            registerMethod()
        }

        coordinateTxt.setOnClickListener{
            getCoordinate()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
        getTextTown()
    }

    fun getCoordinate(){

        val dialog = CoordinateDialog()
        dialog.setListener(this)
        dialog.show(parentFragmentManager, "Give Coordinate")
    }

    fun getViewData(){

        name = nameTl.editText?.text.toString()
        phone = phoneTl.editText?.text.toString()
        email = emailTl.editText?.text.toString()
        password = passwordTl.editText?.text.toString()
        confirmPassword = confirmPasswordTl.editText?.text.toString()
        license = licenseTl.editText?.text.toString()
        town = townTl.editText?.text.toString()
        detail = detailTl.editText?.text.toString()


    }

    fun checkMandatoryField(): Boolean{

        if (coordinate == null){
            Toast.makeText(activity, "Please add coordinate", Toast.LENGTH_LONG).show()
          return false

        }
        else {
            return !name.isNullOrEmpty() && !phone.isNullOrEmpty() && !email.isNullOrEmpty() &&
                    !password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty() && !license.isNullOrEmpty() &&
                    !town.isNullOrEmpty() && !country.isNullOrEmpty() && !serviceOffered.isNullOrEmpty()
        }

    }

    fun registerMethod(){
        getViewData()
        if (checkMandatoryField()){
            if(password == confirmPassword){
                registerProgress.visibility = View.VISIBLE
            val careAdmin = CareAdmin("",name, phone.toLong(), email,license, "",country, town, coordinate,serviceOffered,detail )
            registrationViewModel.signUp(careAdmin, password)
            registrationViewModel.registrationOutput.observe(viewLifecycleOwner,{
                if(it["status"] == "success"){
                    registerProgress.visibility = View.INVISIBLE
                    openInfodialog("Your account has been created, please login", "Success")
                    this.findNavController().navigateUp()
                }else if(it["status"] == "failed"){
                    registerProgress.visibility = View.INVISIBLE
                    openInfodialog(it["value"]!!, "Error")

                }
            })
         }else{
                openInfodialog("Make sure both password are the same", "Error")
            }
        }else{
            openInfodialog("Make sure you have provided all the information", "Error")
        }
    }

    fun openInfodialog(message : String, tag: String){
        val dialog = InfoDialog(message)
        dialog?.show(parentFragmentManager, tag)
    }

    fun bindViews(view: View){
        nameTl = view.findViewById(R.id.name_register_tl)
        phoneTl = view.findViewById(R.id.phone_register_tl)
        emailTl = view.findViewById(R.id.email_register_tl)
        passwordTl = view.findViewById(R.id.password_register_tl)
        confirmPasswordTl = view.findViewById(R.id.confirm_password_register_tl)
        licenseTl = view.findViewById(R.id.licence_register_tl)
        townTl = view.findViewById(R.id.town_register_tl)
        detailTl = view.findViewById(R.id.details_register_tl)
        countrySp = view.findViewById(R.id.country__register_sp)
        serviceOfferedSp = view.findViewById(R.id.service_offered_register_sp)
        coordinateTxt = view.findViewById(R.id.coordinate_register_txt)
        uploadLicenseTxt = view.findViewById(R.id.upload_license_txt)
        registerProgress = view.findViewById(R.id.progressBar_register)
        registerBt = view.findViewById(R.id.register_bt)
        viewAnimator = view.findViewById(R.id.register_view_animator)


        countrySp.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            countrySp.adapter = adapter
        }

        serviceOfferedSp.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            serviceOfferedSp.adapter = adapter
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       if (parent != null){
           if(parent.id == countrySp.id){
               country = parent.getItemAtPosition(position).toString()
           }else if(parent.id == serviceOfferedSp.id){
               serviceOffered = parent.getItemAtPosition(position).toString()
           }



       }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDialogPositiveClick(geoPoint: GeoPoint) {
        coordinate = geoPoint
    }

    fun getTextTown(){

        townTl.editText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(activity?.currentFocus == townTl.editText)
                if(!s.isNullOrEmpty()){
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({ getPlacePredictions(s.toString()) }, 300)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })


    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        adapter.onPlaceClickListener = { geocodePlaceAndDisplay(it) }

    }

    private fun geocodePlaceAndDisplay(it: PredictionAutoComplete) {
        townTl.clearFocus()
        townTl.editText?.setText(it.primaryText)
        viewAnimator.displayedChild = 0
        viewAnimator.visibility = View.GONE
        adapter.predictions.clear()
    }

    private fun getPlacePredictions(query: String) {

        val bias: LocationBias = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596) // NE lat, lng
        )

        // Create a new programmatic Place Autocomplete request in Places SDK for Android
        val newRequest = FindAutocompletePredictionsRequest
            .builder()
            .setSessionToken(sessionToken)
            .setLocationBias(bias)
            .setTypeFilter(TypeFilter.CITIES)
            .setTypeFilter(TypeFilter.REGIONS)
            .setQuery(query)
            .build()

        // Perform autocomplete predictions request
        placesClient.findAutocompletePredictions(newRequest).addOnSuccessListener { response ->
            val predictions = response.autocompletePredictions
            var predictionAutoCompletes = mutableListOf<PredictionAutoComplete>()
            for (prediction in predictions){
                val predictionAutoComplete = PredictionAutoComplete(prediction.getPrimaryText(null).toString(), prediction.getSecondaryText(null).toString())
                predictionAutoCompletes.add(predictionAutoComplete)
            }
            adapter.setPredictions(predictionAutoCompletes)

            viewAnimator.displayedChild = if (predictions.isEmpty()) 0 else 1
            viewAnimator.visibility = View.VISIBLE
        }.addOnFailureListener { exception: Exception? ->

            if (exception is ApiException) {
                Log.e(ContentValues.TAG, "Place not found: " + exception.statusCode)
            }
        }
    }
}
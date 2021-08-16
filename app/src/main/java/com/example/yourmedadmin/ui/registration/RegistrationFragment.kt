package com.example.yourmedadmin.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.CareAdmin
import com.example.yourmedadmin.ui.login.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.GeoPoint


class RegistrationFragment : Fragment(), AdapterView.OnItemSelectedListener {

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

    val registrationViewModel : RegistrationViewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        bindViews(view)
        registerBt.setOnClickListener{
            registerMethod()
        }


        return view
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
            val careAdmin = CareAdmin("",name, phone.toLong(), email,license, "",country, town, coordinate,serviceOffered,detail )
            registrationViewModel.signUp(careAdmin, password)
            registrationViewModel.registrationOutput.observe(viewLifecycleOwner,{
                //show something
            })
         }else{

            }
        }else{

        }
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
           country = parent.getItemAtPosition(position).toString()
           serviceOffered = parent.getItemAtPosition(position).toString()
       }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
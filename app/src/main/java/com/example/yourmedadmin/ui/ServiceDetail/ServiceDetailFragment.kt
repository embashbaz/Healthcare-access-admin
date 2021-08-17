package com.example.yourmedadmin.ui.ServiceDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.data.Service
import com.example.yourmedadmin.ui.dialogs.InfoDialog
import com.google.android.material.textfield.TextInputLayout


class ServiceDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var nameTl: TextInputLayout
    lateinit var identifierTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var priceTl: TextInputLayout
    lateinit var priceDescTl: TextInputLayout
    lateinit var availabilitySp: Spinner
    lateinit var saveBt : Button

    lateinit var name: String
    lateinit var identifier: String
    lateinit var detail: String
    lateinit var priceStr: String
    lateinit var priceDesc: String
    lateinit var availability: String
    var passedService: Service? = null

    val uId: String by lazy {
        (requireActivity().application as  HealthAccessAdmin).uId
    }

    val serviceDetailViewmodel:  ServiceDetailViewmodel by lazy {
        ViewModelProvider(this).get(ServiceDetailViewmodel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_service_detail, container, false)
        bindViews(view)

        if(arguments?.getParcelable<Medicine>("service") != null) {
            passedService = arguments?.getParcelable("service")
            setDataToViews()
        }

        setButtonAction()


        return view
    }

    private fun setButtonAction() {
        if (passedService == null){
            saveBt.setOnClickListener{
                saveService()
            }
        }else{
            saveBt.setOnClickListener{
                updateService()
            }
        }
    }

    private fun updateService() {
        getDataFromViews()
        if(checkMandatoryFields()){
            passedService!!.name = name
            passedService!!.identifier = identifier
            passedService!!.details = detail
            passedService!!.price = priceStr.toDouble()
            passedService!!.priceDescription = priceDesc
            passedService!!.availability = availability
            serviceDetailViewmodel.updateService(uId, passedService!!.uId!!, passedService!!)
            serviceDetailViewmodel.updatingServiceOutput.observe(viewLifecycleOwner,{
                if (it["status"] == "success"){
                    Toast.makeText(activity, "Record updated ", Toast.LENGTH_LONG).show()
                    this.findNavController().navigateUp()

                }else if (it["status"] == "failed"){
                    openInfodialog(it["value"]!!, "Error updating item")
                }
            })

        }else{
            openInfodialog("Only price description, identifier and details can be null", "Failed to add record")
        }
    }

    private fun setDataToViews() {
        saveBt.setText("Update")
        nameTl.editText?.setText(passedService!!.name)
        priceDescTl.editText?.setText(passedService!!.priceDescription)
        identifierTl.editText?.setText(passedService!!.identifier)
        detailTl.editText?.setText(passedService!!.identifier)
        priceTl.editText?.setText(passedService!!.price.toString())
    }

    fun bindViews(view: View){
        nameTl = view.findViewById(R.id.name_service_tl)
        identifierTl = view.findViewById(R.id.identifier_service_tl)
        detailTl = view.findViewById(R.id.details_service_tl)
        priceTl = view.findViewById(R.id.price_service_tl)
        priceDescTl = view.findViewById(R.id.price_desc_service_tl)
        availabilitySp = view.findViewById(R.id.availability_service_detail_sp)
        saveBt = view.findViewById(R.id.save_service)

        availabilitySp.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.availability_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            availabilitySp.adapter = adapter
        }
    }

    fun getDataFromViews(){
        name = nameTl.editText?.text.toString()
        identifier = identifierTl.editText?.text.toString()
        detail = detailTl.editText?.text.toString()
        priceStr = priceTl.editText?.text.toString()
        priceDesc = priceDescTl.editText?.text.toString()
    }

    fun checkMandatoryFields(): Boolean{
        return !name.isNullOrEmpty() && !priceStr.isNullOrEmpty() && !availability.isNullOrEmpty()

    }

    fun saveService(){
        getDataFromViews()
        if(checkMandatoryFields()){
            val service = Service("", name, identifier, detail, priceStr.toDouble(), priceDesc,availability)
            serviceDetailViewmodel.saveNewService(service, uId)
            serviceDetailViewmodel.addingServiceOutput.observe(viewLifecycleOwner, {
                if (it["status"] == "success"){
                    Toast.makeText(activity, "Record added ", Toast.LENGTH_LONG).show()
                    clearFields()

                }else if (it["status"] == "failed"){
                    openInfodialog(it["value"]!!, "Failed to add record")
                }
            })
        }else{
            openInfodialog("Only price description, identifier and details can be null", "Failed to add record")
        }
    }

    fun openInfodialog(message : String, tag: String){
        val dialog = InfoDialog(message)
        dialog?.show(parentFragmentManager, tag)
    }

    fun clearFields(){
        nameTl.editText?.setText("")
        priceDescTl.editText?.setText("")
        identifierTl.editText?.setText("")
        detailTl.editText?.setText("")
        priceTl.editText?.setText("")
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null){
            availability = parent.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}
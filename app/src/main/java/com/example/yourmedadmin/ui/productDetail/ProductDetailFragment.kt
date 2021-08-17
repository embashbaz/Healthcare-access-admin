package com.example.yourmedadmin.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Medicine
import com.google.android.material.textfield.TextInputLayout


class ProductDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var genericNameTl: TextInputLayout
    lateinit var scientificNameTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var priceTl: TextInputLayout
    lateinit var countryManufacturingSp: Spinner
    lateinit var availabilitySp: Spinner
    lateinit var saveBt: Button

    lateinit var genericName: String
    lateinit var scientificName: String
    lateinit var detail: String
    lateinit var priceStr: String
    lateinit var countryManufacturing: String
    lateinit var availability: String

    lateinit var uId: String

    val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(this).get(ProductDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)
        bindViews(view)
        uId = (activity?.application as HealthAccessAdmin).uId
        saveBt.setOnClickListener{
            saveProduct()
        }


        return view
    }

    fun bindViews(view: View){
        genericNameTl = view.findViewById(R.id.generic_name_product_tl)
        scientificNameTl = view.findViewById(R.id.scientific_name_tl)
        detailTl = view.findViewById(R.id.detail_product_tl)
        priceTl = view.findViewById(R.id.price__product_tl)
        countryManufacturingSp = view.findViewById(R.id.manufacturing_country_tl)
        availabilitySp = view.findViewById(R.id.availability_product_spinner)
        saveBt = view.findViewById(R.id.save_product_bt)

        countryManufacturingSp.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            countryManufacturingSp.adapter = adapter
        }

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

    fun getDataFromView(){
        genericName = genericNameTl.editText?.text.toString()
        scientificName = scientificNameTl.editText?.text.toString()
        detail = detailTl.editText?.text.toString()
        priceStr = priceTl.editText?.text.toString()
    }

    fun checkMandatoryFields(): Boolean{
      return !genericName.isNullOrEmpty() && !priceStr.isNullOrEmpty() && !availability.isNullOrEmpty()


    }

    fun saveProduct(){
        getDataFromView()
        if(checkMandatoryFields()){
            val medicine = Medicine(scientificName, genericName,countryManufacturing, detail, availability, "", priceStr.toDouble())
            productDetailViewModel.saveNewProduct(medicine, uId)
            productDetailViewModel.addingProductOutput.observe(viewLifecycleOwner, {
                if (it["status"] == "success"){
                    Toast.makeText(activity, "Record added ", Toast.LENGTH_LONG).show()
                    clearFields()

                }else if (it["status"] == "failed"){
                    //TODO: notice dialog
                }
            })
        }
    }

    fun clearFields(){
        genericNameTl.editText?.setText("")
        scientificNameTl.editText?.setText("")
        detailTl.editText?.setText("")
       priceTl.editText?.setText("")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null){
            countryManufacturing = parent.getItemAtPosition(position).toString()
            availability = parent.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}
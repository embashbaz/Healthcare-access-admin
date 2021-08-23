package com.example.yourmedadmin.ui.productDetail

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Medicine
import com.example.yourmedadmin.ui.dialogs.InfoDialog
import com.example.yourmedadmin.ui.dialogs.NoticeDialog
import com.google.android.material.textfield.TextInputLayout


class ProductDetailFragment : Fragment(), AdapterView.OnItemSelectedListener , NoticeDialog.NoticeDialogListener{

    val DELETE_CODE = 3

    lateinit var genericNameTl: TextInputLayout
    lateinit var scientificNameTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var priceTl: TextInputLayout
    lateinit var countryManufacturingSp: Spinner
    lateinit var availabilitySp: Spinner
    lateinit var saveBt: Button
    lateinit var ignoreBt: Button


    lateinit var genericName: String
    lateinit var scientificName: String
    lateinit var detail: String
    lateinit var priceStr: String
    lateinit var countryManufacturing: String
    lateinit var availability: String
    private val handler = Handler()

    var passedProduct: Medicine? = null

    val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(this).get(ProductDetailViewModel::class.java)
    }

    val uId: String by lazy {
        (requireActivity().application as  HealthAccessAdmin).uId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)
        bindViews(view)
        if(arguments?.getParcelable<Medicine>("medicine") != null) {
            passedProduct = arguments?.getParcelable("medicine")
            setDataToViews()
        }

        setButtonAction()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getItemNameRecomendation()
    }

    private fun setDataToViews() {
        saveBt.setText("Update")
        ignoreBt.setText("Delete Medicine")
        genericNameTl.editText?.setText(passedProduct!!.genericName)
        scientificNameTl.editText?.setText(passedProduct!!.scientificName)
        detailTl.editText?.setText(passedProduct!!.detailsMed)
        priceTl.editText?.setText(passedProduct!!.price.toString())

    }

    fun setButtonAction(){
        if(passedProduct == null)
        {
            saveBt.setOnClickListener{
                saveProduct()
            }

            ignoreBt.setOnClickListener{
                clearFields()
            }
        }else{
            saveBt.setOnClickListener{
                updateMedicine()
            }
            ignoreBt.setOnClickListener{
                deleteButtonPressed()
            }

        }

    }

    private fun deleteButtonPressed(){
        val dialog = NoticeDialog(DELETE_CODE, "Are you sure you want to delete this product", "Yes")
        dialog.setListener(this)
        dialog.show(parentFragmentManager, "Delete Medicine")

    }

    private fun deleteMedicine(){
        productDetailViewModel.deleteMedicine(uId, passedProduct!!.medUid)
        productDetailViewModel.deletingProductOutput.observe(viewLifecycleOwner, {
            if (it["status"] == "success"){
                Toast.makeText(activity, "Record deleted", Toast.LENGTH_LONG).show()
                this.findNavController().navigateUp()

            }else if (it["status"] == "failed"){
                openInfodialog(it["value"]!!, "Error deleting the item")
            }
        })

    }

    private fun updateMedicine() {
        getDataFromView()
        if(checkMandatoryFields()){
            passedProduct!!.scientificName = scientificName
            passedProduct!!.genericName = genericName
            passedProduct!!.countryMade = countryManufacturing
            passedProduct!!.detailsMed = detail
            passedProduct!!.availability = availability
            passedProduct!!.price = priceStr.toDouble()
            productDetailViewModel.updateProduct(uId, passedProduct!!.medUid, passedProduct!!)
            productDetailViewModel.updatingProductOutput.observe(viewLifecycleOwner, {
                if (it["status"] == "success"){
                    Toast.makeText(activity, "Record updated ", Toast.LENGTH_LONG).show()
                    this.findNavController().navigateUp()

                }else if (it["status"] == "failed"){
                    openInfodialog(it["value"]!!, "Error updating item")
                }
            })
        }else{
            openInfodialog("Only manufacturing country, scientific name and details can be null", "Failed to add record")

        }
    }

    fun bindViews(view: View){
        genericNameTl = view.findViewById(R.id.generic_name_product_tl)
        scientificNameTl = view.findViewById(R.id.scientific_name_tl)
        detailTl = view.findViewById(R.id.detail_product_tl)
        priceTl = view.findViewById(R.id.price__product_tl)
        countryManufacturingSp = view.findViewById(R.id.manufacturing_country_sp)
        availabilitySp = view.findViewById(R.id.availability_product_spinner)
        saveBt = view.findViewById(R.id.save_product_bt)
        ignoreBt = view.findViewById(R.id.ignore_product_bt)

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
        genericName = genericNameTl.editText?.text.toString().trim().lowercase()
        scientificName = scientificNameTl.editText?.text.toString().trim().lowercase()
        detail = detailTl.editText?.text.toString().trim().lowercase()
        priceStr = priceTl.editText?.text.toString().trim()
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
                    openInfodialog(it["value"]!!, "Error saving item")
                }
            })
        }else{
            openInfodialog("Only manufacturing country, scientific name and details can be null", "Failed to add record")
        }
    }

    fun clearFields(){
        genericNameTl.editText?.setText("")
        scientificNameTl.editText?.setText("")
        detailTl.editText?.setText("")
       priceTl.editText?.setText("")
    }

    fun openInfodialog(message : String, tag: String){
        val dialog = InfoDialog(message)
        dialog?.show(parentFragmentManager, tag)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null){
            if(parent.id == countryManufacturingSp.id){
                countryManufacturing = parent.getItemAtPosition(position).toString()
            }else if(parent.id == availabilitySp.id){
                availability = parent.getItemAtPosition(position).toString()
            }

        }
    }

    fun getItemNameRecomendation(){
        genericNameTl.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()){

                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({ productDetailViewModel.getRecomendation(s.toString()) }, 300)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })



    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, code: Int) {
        if (code == DELETE_CODE)
            deleteMedicine()

    }


}
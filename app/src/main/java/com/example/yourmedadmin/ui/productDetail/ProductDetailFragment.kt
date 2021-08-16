package com.example.yourmedadmin.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import com.example.yourmedadmin.R
import com.google.android.material.textfield.TextInputLayout


class ProductDetailFragment : Fragment() {

    lateinit var genericNameTl: TextInputLayout
    lateinit var scientificNameTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var priceTl: TextInputLayout
    lateinit var countryManufacturing: Spinner
    lateinit var availabilitySp: Spinner
    lateinit var saveBt: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    fun bindViews(view: View){
        genericNameTl = view.findViewById(R.id.generic_name_product_tl)
        scientificNameTl = view.findViewById(R.id.scientific_name_tl)
        detailTl = view.findViewById(R.id.detail_product_tl)
        priceTl = view.findViewById(R.id.price__product_tl)
        countryManufacturing = view.findViewById(R.id.manufacturing_country_tl)
        availabilitySp = view.findViewById(R.id.availability_product_spinner)
        saveBt = view.findViewById(R.id.save_product_bt)
    }



}
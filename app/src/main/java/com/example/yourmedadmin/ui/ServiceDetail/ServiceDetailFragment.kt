package com.example.yourmedadmin.ui.ServiceDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import com.example.yourmedadmin.R
import com.google.android.material.textfield.TextInputLayout


class ServiceDetailFragment : Fragment() {

    lateinit var nameTl: TextInputLayout
    lateinit var identifierTl: TextInputLayout
    lateinit var detailTl: TextInputLayout
    lateinit var priceTl: TextInputLayout
    lateinit var priceDescTl: TextInputLayout
    lateinit var availabilitySp: Spinner
    lateinit var saveBt : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_detail, container, false)
    }

    fun bindViews(view: View){
        nameTl = view.findViewById(R.id.name_service_tl)
        identifierTl = view.findViewById(R.id.)
        detailTl = view.findViewById(R.id.identifier_service_tl)
        priceTl = view.findViewById(R.id.details_service_tl)
        priceDescTl = view.findViewById(R.id.price_desc_service_tl)
        availabilitySp = view.findViewById(R.id.availability_service_detail_sp)
        saveBt = view.findViewById(R.id.save_service)
    }

}
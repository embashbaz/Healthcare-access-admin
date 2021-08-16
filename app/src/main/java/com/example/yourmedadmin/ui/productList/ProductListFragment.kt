package com.example.yourmedadmin.ui.productList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ProductListFragment : Fragment() {

    lateinit var listRecycler: RecyclerView
    lateinit var noDataTxt: TextView
    lateinit var addProductFb: FloatingActionButton
    lateinit var loadingProgress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    fun bindViews(view: View){
        listRecycler = view.findViewById(R.id.product_list_recycler)
        noDataTxt = view.findViewById(R.id.no_data_product_list)
        addProductFb= view.findViewById(R.id.add_product_fb)
        loadingProgress = view.findViewById(R.id.progressBar_product_list)
    }

}
package com.example.yourmedadmin.ui.serviceList

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

class ServiceListFragment : Fragment() {

    lateinit var listRecycler: RecyclerView
    lateinit var noDataTxt: TextView
    lateinit var addServiceFb: FloatingActionButton
    lateinit var loadingProgress: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_list, container, false)
    }

    fun bindViews(view: View){
        listRecycler = view.findViewById(R.id.service_list_recycler)
        noDataTxt = view.findViewById(R.id.no_data_service_list)
        addServiceFb = view.findViewById(R.id.add_service_fb)
        loadingProgress = view.findViewById(R.id.service_list_progress)
    }

}
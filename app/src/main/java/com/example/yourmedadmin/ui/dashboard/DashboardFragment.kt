package com.example.yourmedadmin.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.yourmedadmin.R


class DashboardFragment : Fragment() {

    lateinit var goToServiceList: CardView
    lateinit var goToProductList: CardView
    lateinit var goToIndemandList: CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
         bindViews(view)
        setViewBehaviour()

        return view
    }

    fun setViewBehaviour(){
        goToServiceList.setOnClickListener{
            this.findNavController().navigate(R.id.action_dashboardFragment_to_serviceListFragment)
        }

        goToProductList.setOnClickListener{
            this.findNavController().navigate(R.id.action_dashboardFragment_to_productListFragment)
        }
    }

    fun bindViews(view: View){
        goToServiceList = view.findViewById(R.id.go_to_service)
        goToProductList = view.findViewById(R.id.go_to_product_list)
        goToIndemandList = view.findViewById(R.id.go_to_indemand)

    }

}
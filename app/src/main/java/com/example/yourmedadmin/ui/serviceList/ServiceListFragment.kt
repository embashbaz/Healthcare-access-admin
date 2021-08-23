package com.example.yourmedadmin.ui.serviceList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Service
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServiceListFragment : Fragment() {

    lateinit var listRecycler: RecyclerView
    lateinit var noDataTxt: TextView
    lateinit var addServiceFb: FloatingActionButton
    lateinit var loadingProgress: ProgressBar
    lateinit var serviceListAdapter: ServiceListAdapter

    val uId: String by lazy {
        (requireActivity().application as HealthAccessAdmin).uId
    }

    val serviceListViewmodel : ServiceListViewmodel by lazy{
        ViewModelProvider(this).get(ServiceListViewmodel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_list, container, false)
        bindViews(view)

        serviceListAdapter = ServiceListAdapter { service -> goToMedicineFragment(service) }
        populateView()

        addServiceFb.setOnClickListener{
            this.findNavController().navigate(R.id.action_serviceListFragment_to_serviceDetailFragment)
        }

        return view
    }

    private fun goToMedicineFragment(service: Service) {
        val bundle = Bundle()
        bundle.putParcelable("service", service)
        this.findNavController().navigate(R.id.action_serviceListFragment_to_serviceDetailFragment, bundle)
    }

    fun bindViews(view: View){
        listRecycler = view.findViewById(R.id.service_list_recycler)
        noDataTxt = view.findViewById(R.id.no_data_service_list)
        addServiceFb = view.findViewById(R.id.add_service_fb)
        loadingProgress = view.findViewById(R.id.service_list_progress)
    }

    private fun populateView() {
        loadingProgress.visibility = View.VISIBLE
        serviceListViewmodel.getServices(uId).observe(viewLifecycleOwner, {
            if(!it.isEmpty()){
                val services = it as ArrayList
                serviceListAdapter.setData(services)
                loadingProgress.visibility = View.INVISIBLE
                listRecycler.visibility = View.VISIBLE
                noDataTxt.visibility = View.INVISIBLE
            }else{
                loadingProgress.visibility = View.INVISIBLE
                listRecycler.visibility = View.INVISIBLE
                noDataTxt.visibility = View.VISIBLE
            }

        })
        listRecycler.layoutManager = LinearLayoutManager(activity)
        listRecycler.adapter = serviceListAdapter


    }

}
package com.example.yourmedadmin.ui.indemandList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.CareAdmin
import com.example.yourmedadmin.data.InDemand
import com.example.yourmedadmin.ui.dialogs.InfoDialog


class IndemandListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var indemandListAdapter: IndemandListAdapter

    val indemandListViewModel : IndemandListViewModel by lazy {
        ViewModelProvider(this).get(IndemandListViewModel::class.java)
    }

    val adminObject: CareAdmin by lazy {
        (activity?.application as HealthAccessAdmin).mCareAdmin as CareAdmin
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_indemand_list, container, false)

        recyclerView = view.findViewById(R.id.indemand_recycler)
        indemandListAdapter = IndemandListAdapter { inDemand -> showItemDetail(inDemand) }

        indemandListViewModel.getproducts(adminObject.country,adminObject.town,"indemand_medicine").observe(viewLifecycleOwner,{
            if(it != null){
                if(it.status == "success") {
                    if(it.statusValue == "success")
                    indemandListAdapter.setData(it.inDemadList as ArrayList<InDemand>)
                    else if (it.statusValue == "failed")
                        openInfodialog("No item has been added","No item")
                }else if(it.status == "failed"){
                    openInfodialog(it.statusValue, "Error")
                }
                }
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = indemandListAdapter


        return view
    }

    fun openInfodialog(message : String, tag: String){
        val dialog = InfoDialog(message)
        dialog?.show(parentFragmentManager, tag)
    }

    private fun showItemDetail(inDemand: InDemand) {

    }

}
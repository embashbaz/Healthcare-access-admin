package com.example.yourmedadmin.ui.indemandList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.InDemand


class IndemandListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var indemandListAdapter: IndemandListAdapter

    val indemandListViewModel : IndemandListViewModel by lazy {
        ViewModelProvider(this).get(IndemandListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_indemand_list, container, false)

        recyclerView = view.findViewById(R.id.indemand_recycler)
        indemandListAdapter = IndemandListAdapter { inDemand -> showItemDetail(inDemand) }

        indemandListViewModel.getproducts("","","").observe(viewLifecycleOwner,{
            if(it != null){
               indemandListAdapter.setData(it.inDemadList as ArrayList<InDemand>)

            }
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = indemandListAdapter


        return view
    }

    private fun showItemDetail(inDemand: InDemand) {

    }

}
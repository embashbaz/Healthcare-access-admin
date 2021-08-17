package com.example.yourmedadmin.ui.productList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.HealthAccessAdmin
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Medicine
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ProductListFragment : Fragment() {

    lateinit var listRecycler: RecyclerView
    lateinit var noDataTxt: TextView
    lateinit var addProductFb: FloatingActionButton
    lateinit var loadingProgress: ProgressBar
    lateinit var listAdapter: ProductsListAdapter

    val uId : String by lazy {
        (requireActivity().application as HealthAccessAdmin).uId
    }

    val productListViewmodel: ProductListViewmodel by lazy {
        ViewModelProvider(this).get(ProductListViewmodel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        bindViews(view)
        listAdapter = ProductsListAdapter { medicine -> goToMedicineDetail(medicine) }
        populateView()


        return view
    }

    private fun goToMedicineDetail(medicine: Medicine) {

    }

    private fun populateView() {
        loadingProgress.visibility = View.VISIBLE
        productListViewmodel.getproducts(uId).observe(viewLifecycleOwner, {
            if(!it.isEmpty()){
                val products = it as ArrayList
                listAdapter.setData(products)
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
        listRecycler.adapter = listAdapter



    }

    fun bindViews(view: View){
        listRecycler = view.findViewById(R.id.product_list_recycler)
        noDataTxt = view.findViewById(R.id.no_data_product_list)
        addProductFb= view.findViewById(R.id.add_product_fb)
        loadingProgress = view.findViewById(R.id.progressBar_product_list)
    }

}
package com.example.yourmedadmin.ui.serviceList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Service
import com.example.yourmedadmin.ui.productList.ProductsListAdapter

class ServiceListAdapter  (onClick: (Service) -> Unit): RecyclerView.Adapter<ServiceListAdapter.ViewHolder>() {

    val mOnclick = onClick
    val allItems = ArrayList<Service>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, mOnclick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = allItems.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount()= allItems.size

    fun setData(items: ArrayList<Service>){
        allItems.clear()
        allItems.addAll(items)
        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View, onClick: (Service) -> Unit) : RecyclerView.ViewHolder(itemView){

        lateinit var mItem: Service
        init{
            itemView.setOnClickListener {
                onClick(mItem)
            }
        }
        fun bind(item: Service){
            mItem = item

            val name = itemView.findViewById<TextView>(R.id.name_product_list)
            val availability = itemView.findViewById<TextView>(R.id.availability_product_list)
            val price = itemView.findViewById<TextView>(R.id.price_product_list)

            name.setText(item.name)
            availability.setText(item.availability)
            price.setText(item.price.toString())

        }

        companion object {
            fun from(parent: ViewGroup, onClick: (Service) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.product_list_item, parent, false)
                return ViewHolder(view, onClick)
            }

        }

    }
}
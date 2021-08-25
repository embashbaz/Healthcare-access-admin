package com.example.yourmedadmin.ui.indemandList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.InDemand

class IndemandListAdapter (onClick: (InDemand) -> Unit): RecyclerView.Adapter<IndemandListAdapter.ViewHolder>() {

    val mOnclick = onClick
    val allItems = ArrayList<InDemand>()



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

    fun setData(items: ArrayList<InDemand>){
        allItems.clear()
        allItems.addAll(items)
        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View, onClick: (InDemand) -> Unit) : RecyclerView.ViewHolder(itemView){

        lateinit var mItem: InDemand
        init{
            itemView.setOnClickListener {
                onClick(mItem)
            }
        }
        fun bind(item: InDemand){
            mItem = item

            val name = itemView.findViewById<TextView>(R.id.service_name_indemand_item_txt)
            val availability = itemView.findViewById<TextView>(R.id.availability_product_list)
            val price = itemView.findViewById<TextView>(R.id.number_request)

            name.setText(item.serviceName)
            //availability.setText(item.availability)
            price.setText(item.numberRequest.toString())

        }

        companion object {
            fun from(parent: ViewGroup, onClick: (InDemand) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.product_list_item, parent, false)
                return ViewHolder(view, onClick)
            }

        }

    }
}
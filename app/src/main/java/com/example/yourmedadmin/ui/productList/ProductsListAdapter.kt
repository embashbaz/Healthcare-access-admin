package com.example.yourmedadmin.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.Medicine

class ProductsListAdapter (onClick: (Medicine) -> Unit): RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {

    val mOnclick = onClick
    val allItems = ArrayList<Medicine>()



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

    fun setData(items: ArrayList<Medicine>){
        allItems.clear()
        allItems.addAll(items)
        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View, onClick: (Medicine) -> Unit) : RecyclerView.ViewHolder(itemView){

        lateinit var mItem: Medicine
        init{
            itemView.setOnClickListener {
                onClick(mItem)
            }
        }
        fun bind(item: Medicine){
            mItem = item

            val name = itemView.findViewById<TextView>(R.id.name_product_list)
            val availability = itemView.findViewById<TextView>(R.id.availability_product_list)
            val price = itemView.findViewById<TextView>(R.id.price_product_list)

            name.setText(item.genericName)
            availability.setText(item.availability)
            price.setText(item.price.toString())

        }

        companion object {
            fun from(parent: ViewGroup, onClick: (Medicine) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.product_list_item, parent, false)
                return ViewHolder(view, onClick)
            }

        }

    }
}
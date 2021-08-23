package com.example.yourmedadmin.ui.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmedadmin.R
import com.example.yourmedadmin.data.PredictionAutoComplete

class PredictionAdapter : RecyclerView.Adapter<PredictionAdapter.PlacePredictionViewHolder>() {
    val predictions: MutableList<PredictionAutoComplete> = ArrayList()
    var onPlaceClickListener: ((PredictionAutoComplete) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacePredictionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacePredictionViewHolder(
                inflater.inflate(R.layout.search_item_recommendation, parent, false))
    }

    override fun onBindViewHolder(holder: PlacePredictionViewHolder, position: Int) {
        val place = predictions[position]
        holder.setPrediction(place)
        holder.itemView.setOnClickListener {
            onPlaceClickListener?.invoke(place)
        }
    }

    override fun getItemCount(): Int {
        return predictions.size
    }

    fun setPredictions(predictions: List<PredictionAutoComplete>?) {
        this.predictions.clear()
        this.predictions.addAll(predictions!!)
        notifyDataSetChanged()
    }

    class PlacePredictionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text_view_title)
        private val address: TextView = itemView.findViewById(R.id.text_view_secondary_text)

        fun setPrediction(prediction: PredictionAutoComplete) {
            title.text = prediction.primaryText
            address.text = prediction.secondaryText
        }
    }


}
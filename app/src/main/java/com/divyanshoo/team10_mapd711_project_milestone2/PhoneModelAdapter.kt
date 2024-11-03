// PhoneModelAdapter.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhoneModelAdapter(private val phoneModels: List<PhoneModel>) : RecyclerView.Adapter<PhoneModelAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelName: TextView = itemView.findViewById(R.id.modelName)
        val modelPrice: TextView = itemView.findViewById(R.id.modelPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_phone_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phoneModel = phoneModels[position]
        holder.modelName.text = phoneModel.name
        holder.modelPrice.text = phoneModel.price
    }

    override fun getItemCount(): Int {
        return phoneModels.size
    }
}

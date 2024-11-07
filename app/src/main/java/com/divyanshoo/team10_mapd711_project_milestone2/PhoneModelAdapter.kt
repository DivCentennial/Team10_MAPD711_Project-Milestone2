// PhoneModelAdapter.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhoneModelAdapter(
    private val phoneModels: List<PhoneModel>,
    private val onItemClick: (PhoneModel) -> Unit // Lambda for item click handling
) : RecyclerView.Adapter<PhoneModelAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION // Track selected item position

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelName: TextView = itemView.findViewById(R.id.modelName)
        val modelPrice: TextView = itemView.findViewById(R.id.modelPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_phone_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val phoneModel = phoneModels[position]
        holder.modelName.text = phoneModel.name
        holder.modelPrice.text = phoneModel.price

        // Set background color based on selection
        holder.itemView.setBackgroundColor(
            if (position == selectedPosition) Color.parseColor("#8A285D") // Darker shade for selected item
            else Color.TRANSPARENT // Default color for unselected items
        )

        // Set up the click listener for each item
        holder.itemView.setOnClickListener {
            // Update selected position and notify adapter to refresh views
            val previousSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            onItemClick(phoneModel) // Pass the clicked item to the lambda
        }
    }

    override fun getItemCount(): Int {
        return phoneModels.size
    }
}

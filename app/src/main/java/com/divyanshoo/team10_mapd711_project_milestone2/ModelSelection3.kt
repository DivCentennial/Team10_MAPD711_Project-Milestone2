// ModelSelection3.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ModelSelection3 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneModelAdapter: PhoneModelAdapter
    private lateinit var phoneModels: List<PhoneModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_model_selection3)

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the selected brand
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND")

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create phone models based on selected brand
        phoneModels = when (selectedBrand) {
            "Samsung" -> listOf(
                PhoneModel("Galaxy S23", "$799"),
                PhoneModel("Galaxy Z Fold 5", "$1,799"),
                PhoneModel("Galaxy Z Fold 6", "$1,899"),
                PhoneModel("Galaxy S23 Ultra", "$1099")
            )
            "Google Pixel" -> listOf(
                PhoneModel("Google Pixel 8", "$699"),
                PhoneModel("Google Pixel 7 Pro", "$899"),
                PhoneModel("Google Pixel 6", "$599"),
                PhoneModel("Google Pixel 9", "$1099")
            )
            "iPhone" -> listOf(
                PhoneModel("iPhone 14", "$999"),
                PhoneModel("iPhone 15", "$1,099"),
                PhoneModel("iPhone 15 Pro", "$1,199"),
                PhoneModel("iPhone 16", "$1,299")
            )
            else -> emptyList()
        }

        // Set the adapter to the RecyclerView
        phoneModelAdapter = PhoneModelAdapter(phoneModels)
        recyclerView.adapter = phoneModelAdapter
    }
}

// ModelSelection3.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ModelSelection3 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneModelAdapter: PhoneModelAdapter
    private lateinit var phoneModels: List<PhoneModel>
    private var selectedModel: PhoneModel? = null // Holds the selected model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_selection3)

        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get selected brand from intent and set up phone models
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND")
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

        // Display selected model to the user
        val selectedModelTextView = findViewById<TextView>(R.id.selectedModelTextView)

        // Set up adapter with onItemClick lambda to select a model without navigating
        phoneModelAdapter = PhoneModelAdapter(phoneModels) { model ->
            selectedModel = model
            selectedModelTextView.text = "Selected Model: ${model.name} - ${model.price}"
        }
        recyclerView.adapter = phoneModelAdapter

        // Set up storage options RadioGroup
        val radioGroupStorage = findViewById<RadioGroup>(R.id.radioGroupStorage)
        val buttonNext = findViewById<Button>(R.id.button4)

        // Next button listener to check selections and navigate
        buttonNext.setOnClickListener {
            if (selectedModel == null) {
                Toast.makeText(this, "Please select a phone model", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedStorageId = radioGroupStorage.checkedRadioButtonId
            if (selectedStorageId == -1) {
                Toast.makeText(this, "Please select a storage option", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the selected storage option
            val selectedStorage = findViewById<RadioButton>(selectedStorageId).text.toString()

            // Pass selected model, brand, price, and storage to the next activity
            val intent = Intent(this, ColorSelection4::class.java).apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel?.name)
                putExtra("SELECTED_PRICE", selectedModel?.price)
                putExtra("SELECTED_STORAGE", selectedStorage)
            }
            startActivity(intent)
        }
    }
}

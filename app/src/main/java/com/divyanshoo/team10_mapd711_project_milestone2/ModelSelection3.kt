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
    private var selectedModel: PhoneModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_selection3)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: ""

        phoneModels = getPhoneModelsForBrand(selectedBrand)

        val selectedModelTextView = findViewById<TextView>(R.id.selectedModelTextView)
        val radioGroupStorage = findViewById<RadioGroup>(R.id.radioGroupStorage)
        val finalPriceTextView = findViewById<TextView>(R.id.textView5)
        val buttonNext = findViewById<Button>(R.id.colorSelectionButton)

        phoneModelAdapter = PhoneModelAdapter(phoneModels) { model ->
            selectedModel = model
            selectedModelTextView.text = "Selected Model: ${model.name} - $${model.price}"
            finalPriceTextView.text = "" // Clear the final price when model changes
        }
        recyclerView.adapter = phoneModelAdapter

        // Listen for storage selection
        radioGroupStorage.setOnCheckedChangeListener { _, checkedId ->
            if (selectedModel != null && checkedId != -1) {
                val selectedStorage = findViewById<RadioButton>(checkedId).text.toString()
                val basePrice = selectedModel?.price?.toIntOrNull() ?: 0

                val additionalCost = when (selectedStorage) {
                    "128 GB" -> 30
                    "256 GB" -> 50
                    else -> 0
                }
                val finalPrice = basePrice + additionalCost
                finalPriceTextView.text = "Final Price: $$finalPrice"
            } else if (checkedId != -1) {
                Toast.makeText(this, "Please select a phone model first", Toast.LENGTH_SHORT).show()
                radioGroupStorage.clearCheck()
            }
        }

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

            val selectedStorage = findViewById<RadioButton>(selectedStorageId).text.toString()
            val basePrice = selectedModel?.price?.toIntOrNull() ?: 0

            val additionalCost = when (selectedStorage) {
                "128 GB" -> 30
                "256 GB" -> 50
                else -> 0
            }
            val finalPrice = basePrice + additionalCost

            val intent = Intent(this, ColorSelection4::class.java).apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel?.name)
                putExtra("SELECTED_PRICE", finalPrice.toString())
                putExtra("SELECTED_STORAGE", selectedStorage)
            }
            startActivity(intent)
        }
    }

    private fun getPhoneModelsForBrand(brand: String): List<PhoneModel> {
        return when (brand) {
            "Samsung" -> listOf(
                PhoneModel("Galaxy S23", "799"),
                PhoneModel("Galaxy Z Fold 5", "1799"),
                PhoneModel("Galaxy Z Fold 6", "1899"),
                PhoneModel("Galaxy S23 Ultra", "1099")
            )
            "Google Pixel" -> listOf(
                PhoneModel("Google Pixel 8", "699"),
                PhoneModel("Google Pixel 7 Pro", "899"),
                PhoneModel("Google Pixel 6", "599"),
                PhoneModel("Google Pixel 9", "1099")
            )
            "iPhone" -> listOf(
                PhoneModel("iPhone 14", "999"),
                PhoneModel("iPhone 15", "1099"),
                PhoneModel("iPhone 15 Pro", "1199"),
                PhoneModel("iPhone 16", "1299")
            )
            else -> emptyList()
        }
    }
}


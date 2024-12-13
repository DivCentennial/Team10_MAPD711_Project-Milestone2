package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.PhoneModel
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.ModelSelection3ViewModel

class ModelSelection3 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneModelAdapter: PhoneModelAdapter
    private val modelSelection3ViewModel: ModelSelection3ViewModel by viewModels()
    private var selectedModel: PhoneModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_selection3)

        recyclerView = findViewById(R.id.recyclerView)
        val selectedModelTextView = findViewById<TextView>(R.id.selectedModelTextView)
        val radioGroupStorage = findViewById<RadioGroup>(R.id.radioGroupStorage)
        val finalPriceTextView = findViewById<TextView>(R.id.textView5)
        val buttonNext = findViewById<Button>(R.id.colorSelectionButton)

        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: ""

        modelSelection3ViewModel.loadPhoneModelsForBrand(selectedBrand)

        // Observe phone models list from ViewModel
        modelSelection3ViewModel.phoneModels.observe(this) { phoneModels ->
            if (phoneModels.isEmpty()) {
                Toast.makeText(this, "No models available for the selected brand.", Toast.LENGTH_SHORT).show()
                return@observe
            }

            recyclerView.layoutManager = LinearLayoutManager(this)
            phoneModelAdapter = PhoneModelAdapter(phoneModels) { model ->
                selectedModel = model
                selectedModelTextView.text = "Selected Model: ${model.name} - $${model.price}"
                finalPriceTextView.text = "" // Clear final price when model changes
                modelSelection3ViewModel.selectPhoneModel(model)
            }
            recyclerView.adapter = phoneModelAdapter
        }

        radioGroupStorage.setOnCheckedChangeListener { _, checkedId ->
            if (selectedModel != null && checkedId != -1) {
                val selectedStorage = findViewById<RadioButton>(checkedId).text.toString()
                modelSelection3ViewModel.calculateFinalPrice(selectedStorage)
            } else if (checkedId != -1) {
                Toast.makeText(this, "Please select a phone model first", Toast.LENGTH_SHORT).show()
                radioGroupStorage.clearCheck()
            }
        }

        modelSelection3ViewModel.finalPrice.observe(this) { finalPrice ->
            finalPriceTextView.text = finalPrice
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

            // Save details to database through ViewModel
            modelSelection3ViewModel.savePhoneDetailsToDatabase(
                selectedBrand = selectedBrand,
                selectedStorage = selectedStorage
            )

            val intent = Intent(this, ColorSelection4::class.java).apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel?.name)
                putExtra("SELECTED_PRICE", finalPriceTextView.text.toString())
                putExtra("SELECTED_STORAGE", selectedStorage)
            }
            startActivity(intent)
        }
    }
}

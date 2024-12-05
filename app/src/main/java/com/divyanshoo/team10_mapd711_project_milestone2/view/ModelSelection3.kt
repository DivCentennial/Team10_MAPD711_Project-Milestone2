package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import com.divyanshoo.team10_mapd711_project_milestone2.model.PhoneModel
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ModelSelection3 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneModelAdapter: PhoneModelAdapter
    private lateinit var phoneRepository: PhoneRepository
    private var phoneModels: List<PhoneModel> = emptyList()
    private var selectedModel: PhoneModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_selection3)

        phoneRepository = PhoneRepository(this)

        recyclerView = findViewById(R.id.recyclerView)
        val selectedModelTextView = findViewById<TextView>(R.id.selectedModelTextView)
        val radioGroupStorage = findViewById<RadioGroup>(R.id.radioGroupStorage)
        val finalPriceTextView = findViewById<TextView>(R.id.textView5)
        val buttonNext = findViewById<Button>(R.id.colorSelectionButton)

        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: ""

        phoneModels = phoneRepository.getPhoneModelsForBrand(selectedBrand)
        if (phoneModels.isEmpty()) {
            Toast.makeText(this, "No models available for the selected brand.", Toast.LENGTH_SHORT).show()
            return
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        phoneModelAdapter = PhoneModelAdapter(phoneModels) { model ->
            selectedModel = model
            selectedModelTextView.text = "Selected Model: ${model.name} - $${model.price}"
            finalPriceTextView.text = "" // Clear final price when model changes
        }
        recyclerView.adapter = phoneModelAdapter

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

            // Save details to database
            lifecycleScope.launch {
                savePhoneDetailsToDatabase(
                    selectedBrand = selectedBrand,
                    selectedModel = selectedModel?.name ?: "",
                    selectedStorage = selectedStorage,
                    finalPrice = finalPrice
                )
            }

            val intent = Intent(this, ColorSelection4::class.java).apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel?.name)
                putExtra("SELECTED_PRICE", finalPrice.toString())
                putExtra("SELECTED_STORAGE", selectedStorage)
            }
            startActivity(intent)
        }
    }

    private suspend fun savePhoneDetailsToDatabase(
        selectedBrand: String,
        selectedModel: String,
        selectedStorage: String,
        finalPrice: Int
    ) {
        withContext(Dispatchers.IO) {
            val phoneDao = AppDatabase.getDatabase(applicationContext).phoneDao()
            val phone = Phone(
                productId = UUID.randomUUID(),
                phoneMake = selectedBrand,
                phoneModel = selectedModel,
                phoneColor = "", // Color will be selected in the next screen
                storageCapacity = selectedStorage,
                price = finalPrice.toString()
            )
            // Log the phone data to verify it's being passed correctly
            Log.d("PhoneInsertion", "Saving phone: $phone")
            phoneDao.insertPhone(phone)

            // Query all phones to verify the insert
            val allPhones = phoneDao.getAllPhones()
            Log.d("PhoneInsertion", "All phones in DB: $allPhones")
        }
    }
}

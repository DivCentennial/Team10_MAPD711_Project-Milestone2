// BrandSelectionActivity2.kt
package com.divyanshoo.team10_mapd711_project_milestone2.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class BrandSelectionActivity2 : AppCompatActivity() {
    private lateinit var phoneImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_selection2)

        val spinner: Spinner = findViewById(R.id.spinner)
        phoneImage = findViewById(R.id.phoneImage)

        // Hardcoded brands for simplicity
        val brands = listOf("Samsung", "Google Pixel", "iPhone")

        // Create ArrayAdapter using the brands
        val adapter = ArrayAdapter(this, R.layout.spinner_item, brands)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Handle brand selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (brands[position]) {
                    "Samsung" -> phoneImage.setImageResource(R.drawable.samsung_image)
                    "Google Pixel" -> phoneImage.setImageResource(R.drawable.google_pixel_image)
                    "iPhone" -> phoneImage.setImageResource(R.drawable.iphone_image)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Navigate to ModelSelection3 with the selected brand and save it to the database
        val proceedButton: Button = findViewById(R.id.button)
        proceedButton.setOnClickListener {
            val selectedBrand = spinner.selectedItem.toString()

            // Save selected brand to the database
            lifecycleScope.launch {
                saveSelectedBrandToDatabase(selectedBrand)

                // Navigate to the next activity
                val intent = Intent(this@BrandSelectionActivity2, ModelSelection3::class.java)
                intent.putExtra("SELECTED_BRAND", selectedBrand)
                startActivity(intent)
            }
        }
    }

    // Function to save the selected brand to the database
    private suspend fun saveSelectedBrandToDatabase(brand: String) {
        withContext(Dispatchers.IO) {
            val phoneDao = AppDatabase.getDatabase(applicationContext).phoneDao()

            // Check if a phone with the same brand already exists
            val existingPhone = phoneDao.getPhonesByBrand(brand).firstOrNull()
            if (existingPhone == null) {
                val phone = Phone(
                    productId = UUID.randomUUID(),
                    phoneMake = brand,
                    phoneModel = "",
                    phoneColor = "",
                    storageCapacity = "",
                    price = ""
                )

                // Log the selected brand
                Log.d("BrandSelectionActivity2", "Saving brand: $brand")

                // Save the phone details to the database
                phoneDao.insertPhone(phone)
            } else {
                // Log if the brand already exists
                Log.d("BrandSelectionActivity2", "Brand already exists in the database: $brand")
            }
        }
    }


}
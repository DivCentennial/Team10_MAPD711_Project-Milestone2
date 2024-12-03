//  BrandSelectionActivity2.kt
// View description: User can select desired brands.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import ModelSelection3
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository

class BrandSelectionActivity2 : AppCompatActivity() {
    private lateinit var phoneRepository: PhoneRepository
    private lateinit var phoneImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_selection2)

        phoneRepository = PhoneRepository(this) // Initialize repository

        val spinner: Spinner = findViewById(R.id.spinner)
        phoneImage = findViewById(R.id.phoneImage)

        // Get brands from repository
        val brands = phoneRepository.getPhoneBrands()

        // Create ArrayAdapter using brands from the repository
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

        // Navigate to ModelSelection3 with the selected brand
        val proceedButton: Button = findViewById(R.id.button)
        proceedButton.setOnClickListener {
            val selectedBrand = spinner.selectedItem.toString()
            val intent = Intent(this, ModelSelection3::class.java)
            intent.putExtra("SELECTED_BRAND", selectedBrand)
            startActivity(intent)
        }
    }
}
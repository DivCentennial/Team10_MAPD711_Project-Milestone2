//  BrandSelectionActivity2.kt
// View description: User can select desired brands.
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BrandSelectionActivity2 : AppCompatActivity() {
    private lateinit var phoneImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_brand_selection2)

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Spinner and ImageView
        val spinner: Spinner = findViewById(R.id.spinner)
        phoneImage = findViewById(R.id.phoneImage)

        // Set a listener on the spinner to update the image based on selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> phoneImage.setImageResource(R.drawable.samsung_image) // Samsung
                    1 -> phoneImage.setImageResource(R.drawable.google_pixel_image) // Google Pixel
                    2 -> phoneImage.setImageResource(R.drawable.iphone_image) // iPhone
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Handle Proceed button click to navigate to ModelSelection3.kt
        val proceedButton: Button = findViewById(R.id.button)
        proceedButton.setOnClickListener {
            val selectedBrand = spinner.selectedItem.toString()
            val intent = Intent(this, ModelSelection3::class.java)
            intent.putExtra("SELECTED_BRAND", selectedBrand)
            startActivity(intent)
        }
    }
}

//color_selection4.kt

// View description: User can select desired color.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.divyanshoo.team10_mapd711_project_milestone2.R

class ColorSelection4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_selection4)

        // Edge-to-edge configuration
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve selected phone details from Intent
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND")
        val selectedModel = intent.getStringExtra("SELECTED_MODEL")
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE")
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE")

        // Log received data
        Log.d("ColorSelection4", "Selected Brand: $selectedBrand")
        Log.d("ColorSelection4", "Selected Model: $selectedModel")
        Log.d("ColorSelection4", "Selected Storage: $selectedStorage")
        Log.d("ColorSelection4", "Selected Price: $selectedPrice")

        // Reference to the RadioGroup
        val colorRadioGroup: RadioGroup = findViewById(R.id.colorRadioGroup)

        // Set up the "Next" button
        val nextButton: Button = findViewById(R.id.checkoutButton)
        nextButton.setOnClickListener {
            // Get the selected radio button
            val selectedRadioButtonId = colorRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId == -1) {
                Toast.makeText(this, "Please select a color", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val selectedColor = findViewById<RadioButton>(selectedRadioButtonId).text.toString()

            // Log the selected color
            Log.d("ColorSelection4", "Selected Color: $selectedColor")

            // Pass data to CheckoutActivity
            val intent = Intent(this, CheckoutActivity5::class.java)
            intent.putExtra("SELECTED_BRAND", selectedBrand)
            intent.putExtra("SELECTED_MODEL", selectedModel)
            intent.putExtra("SELECTED_PRICE", selectedPrice)
            intent.putExtra("SELECTED_STORAGE", selectedStorage)
            intent.putExtra("SELECTED_COLOR", selectedColor)
            startActivity(intent)
        }
    }
}

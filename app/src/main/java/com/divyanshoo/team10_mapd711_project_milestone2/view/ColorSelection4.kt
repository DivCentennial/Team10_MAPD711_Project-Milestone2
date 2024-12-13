//color_selection4.kt

// View description: User can select desired color.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.ColorSelectionViewModel

class ColorSelection4 : AppCompatActivity() {

    private val colorSelectionViewModel: ColorSelectionViewModel by viewModels() // Get ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_selection4)

        // Retrieve selected phone details from Intent
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND")
        val selectedModel = intent.getStringExtra("SELECTED_MODEL")
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE")
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE")

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

            // Save the phone data via ViewModel
            colorSelectionViewModel.savePhone(selectedBrand, selectedModel, selectedColor, selectedStorage, selectedPrice)

            // Observe the ViewModel to show a message after saving
            colorSelectionViewModel.phoneSavedLiveData.observe(this, { isSaved ->
                if (isSaved) {
                    // Pass data to CheckoutActivity
                    val intent = Intent(this, CheckoutActivity5::class.java)
                    intent.putExtra("SELECTED_BRAND", selectedBrand)
                    intent.putExtra("SELECTED_MODEL", selectedModel)
                    intent.putExtra("SELECTED_PRICE", selectedPrice)
                    intent.putExtra("SELECTED_STORAGE", selectedStorage)
                    intent.putExtra("SELECTED_COLOR", selectedColor)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Failed to save phone", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

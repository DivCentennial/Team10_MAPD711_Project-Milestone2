import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.view.PhoneModelAdapter
import com.divyanshoo.team10_mapd711_project_milestone2.model.PhoneModel
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import com.divyanshoo.team10_mapd711_project_milestone2.view.ColorSelection4

class ModelSelection3 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneModelAdapter: PhoneModelAdapter
    private lateinit var phoneModels: List<PhoneModel>
    private var selectedModel: PhoneModel? = null

    // Use the repository to handle data fetching
    private lateinit var phoneRepository: PhoneRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_selection3)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get the selected brand from the previous screen
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: ""

        // Fetch phone models for the selected brand using the repository
        phoneModels = phoneRepository.getPhoneModelsForBrand(selectedBrand)

        val selectedModelTextView = findViewById<TextView>(R.id.selectedModelTextView)
        val radioGroupStorage = findViewById<RadioGroup>(R.id.radioGroupStorage)
        val finalPriceTextView = findViewById<TextView>(R.id.textView5)
        val buttonNext = findViewById<Button>(R.id.colorSelectionButton)

        // Set up the RecyclerView with the adapter
        phoneModelAdapter = PhoneModelAdapter(phoneModels) { model ->
            selectedModel = model
            selectedModelTextView.text = "Selected Model: ${model.name} - $${model.price}"
            finalPriceTextView.text = "" // Clear the final price when the model changes
        }
        recyclerView.adapter = phoneModelAdapter

        // Listen for storage selection and update the final price
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

        // Handle the Next button click
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

            // Navigate to the next screen and pass the selected data
            val intent = Intent(this, ColorSelection4::class.java).apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel?.name)
                putExtra("SELECTED_PRICE", finalPrice.toString())
                putExtra("SELECTED_STORAGE", selectedStorage)
            }
            startActivity(intent)
        }
    }
}

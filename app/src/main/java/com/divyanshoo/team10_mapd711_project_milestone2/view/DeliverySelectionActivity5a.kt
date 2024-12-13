//DeliverySelectionActivity5a
// User can select the Campus Pickup or the home delivery,if campus selected then map is shown if home then not shown.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.DeliverySelectionViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class DeliverySelectionActivity5a : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationSpinner: Spinner
    private lateinit var satelliteSwitch: Switch
    private val viewModel: DeliverySelectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_selection_activity5a)

        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"

        viewModel.selectedBrand.value = selectedBrand
        viewModel.selectedModel.value = selectedModel
        viewModel.selectedPrice.value = selectedPrice
        viewModel.selectedStorage.value = selectedStorage
        viewModel.selectedColor.value = selectedColor

        val deliveryOptions = arrayOf("Door Delivery", "Order Pickup")
        val deliverySpinner: Spinner = findViewById(R.id.deliverySpinner)

        // Spinner adapter for delivery options
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            deliveryOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deliverySpinner.adapter = adapter

        // View references
        val mapFragmentContainer = findViewById<FrameLayout>(R.id.mapFragmentContainer)
        locationSpinner = findViewById(R.id.campusSpinner)
        satelliteSwitch = findViewById(R.id.satelliteSwitch)
        val proceedButton: Button = findViewById(R.id.proceedButton)
        val textViewCampus = findViewById<TextView>(R.id.textViewCampus)

        // Initialize the map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragmentContainer) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mapFragmentContainer, it)
                    .commit()
            }

        // Initialize map asynchronously
        mapFragment.getMapAsync(this)

        // Initially hide map and related UI
        mapFragmentContainer.visibility = View.GONE
        locationSpinner.visibility = View.GONE
        textViewCampus.visibility = View.GONE
        satelliteSwitch.visibility = View.GONE

        deliverySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onDeliveryOptionSelected(position)
                if (position == 1) {  // "Order Pickup"
                    mapFragmentContainer.visibility = View.VISIBLE
                    locationSpinner.visibility = View.VISIBLE
                    textViewCampus.visibility = View.VISIBLE
                    satelliteSwitch.visibility = View.VISIBLE
                    // Observe campus locations and update spinner
                    viewModel.campusLocations.observe(this@DeliverySelectionActivity5a) { campuses ->
                        // Check if campuses data is available
                        if (campuses.isNotEmpty()) {
                            locationSpinner.adapter = ArrayAdapter(
                                this@DeliverySelectionActivity5a,
                                R.layout.spinner_item,  // Custom spinner item layout for the text
                                campuses.map { it.name }
                            ).apply {
                                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)  // Default drop-down layout
                            }
                        } else {
                            // Fallback if no campuses are available
                            locationSpinner.adapter = ArrayAdapter(
                                this@DeliverySelectionActivity5a,
                                R.layout.spinner_item,
                                listOf("No campuses available")
                            )
                        }
                    }
                } else {
                    mapFragmentContainer.visibility = View.GONE
                    locationSpinner.visibility = View.GONE
                    textViewCampus.visibility = View.GONE
                    satelliteSwitch.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        proceedButton.setOnClickListener {
            val selectedDeliveryOption = deliverySpinner.selectedItem.toString()
            val intent = Intent(this, PaymentInfoActivity7::class.java).apply {
                // Pass phone details and delivery type
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel)
                putExtra("SELECTED_PRICE", selectedPrice)
                putExtra("SELECTED_STORAGE", selectedStorage)
                putExtra("SELECTED_COLOR", selectedColor)
                putExtra("DELIVERY_TYPE", selectedDeliveryOption)

                // If Campus Pickup, pass campus details
                if (selectedDeliveryOption == "Order Pickup" && locationSpinner.selectedItem != null) {
                    val selectedCampus = viewModel.campusLocations.value?.get(locationSpinner.selectedItemPosition)
                    selectedCampus?.let {
                        putExtra("CAMPUS_NAME", it.name)
                        putExtra("CAMPUS_ADDRESS", it.address)
                    }
                }
            }
            startActivity(intent)
        }

        // Ensure googleMap is initialized before using it
        satelliteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (::googleMap.isInitialized) {
                viewModel.updateSatelliteView(isChecked, googleMap)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Load campus markers if available
        viewModel.campusLocations.value?.let {
            // Default to Morningside campus
            viewModel.updateCampusLocationOnMap(googleMap, 0)
        }

        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (::googleMap.isInitialized) {
                    viewModel.updateCampusLocationOnMap(googleMap, position)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}

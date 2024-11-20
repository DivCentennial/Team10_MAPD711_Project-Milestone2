//delivery_selection_activity5a.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DeliverySelectionActivity5a : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationSpinner: Spinner

    // Campus data
    private val campusLocations = listOf(
        Campus("Morningside", LatLng(43.78713095599446, -79.18935308080529), "755 Morningside Ave, Scarborough, ON M1C 4Z4"),
        Campus("Downsview", LatLng(43.74804023006045, -79.47487355889258), "65 Carl Hall Rd, Toronto, ON M3K 2C1"),
        Campus("Ashtonbee", LatLng(43.730346082748056, -79.29202483005702), "75 Ashtonbee Rd, Scarborough, ON M1L 4C9"),
        Campus("Progress", LatLng(43.78538241881133, -79.22812618772701), "941 Progress Ave, Scarborough, ON M1G 3T8"),
        Campus("SAC", LatLng(43.69026700487268, -79.34687459617166), "951 Carlaw Ave, Toronto, ON M4K 3M2")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_selection_activity5a)

        // Retrieve phone details from intent
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"

        // Setup delivery option spinner
        val deliveryOptions = arrayOf("Door Delivery", "Campus Pickup")
        val deliverySpinner: Spinner = findViewById(R.id.deliverySpinner)
        deliverySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            deliveryOptions
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Campus-related views
        val mapFragmentContainer = findViewById<FrameLayout>(R.id.mapFragmentContainer)
        locationSpinner = findViewById(R.id.campusSpinner)
        val proceedButton: Button = findViewById(R.id.proceedButton)

        // Hide campus-related views initially
        mapFragmentContainer.visibility = View.GONE
        locationSpinner.visibility = View.GONE

        // Set up map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Listener for delivery option selection
        deliverySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (deliveryOptions[position] == "Campus Pickup") {
                    mapFragmentContainer.visibility = View.VISIBLE
                    locationSpinner.visibility = View.VISIBLE

                    // Setup campus spinner
                    locationSpinner.adapter = ArrayAdapter(
                        this@DeliverySelectionActivity5a,
                        android.R.layout.simple_spinner_item,
                        campusLocations.map { it.name }
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                } else {
                    mapFragmentContainer.visibility = View.GONE
                    locationSpinner.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Listener for proceed button
        proceedButton.setOnClickListener {
            val selectedDeliveryOption = deliverySpinner.selectedItem.toString()
            val intent = Intent(this, CustomerInfoActivity6::class.java)

            // Add common details
            intent.apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel)
                putExtra("SELECTED_PRICE", selectedPrice)
                putExtra("SELECTED_STORAGE", selectedStorage)
                putExtra("SELECTED_COLOR", selectedColor)
                putExtra("DELIVERY_TYPE", selectedDeliveryOption)

                // Add campus details if Campus Pickup is selected
                if (selectedDeliveryOption == "Campus Pickup" && locationSpinner.selectedItem != null) {
                    val selectedCampus = campusLocations[locationSpinner.selectedItemPosition]
                    putExtra("CAMPUS_NAME", selectedCampus.name)
                    putExtra("CAMPUS_ADDRESS", selectedCampus.address)
                }
            }
            startActivity(intent)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        updateMap(campusLocations[0]) // Load first campus by default

        // Campus spinner selection listener
        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateMap(campusLocations[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateMap(campus: Campus) {
        googleMap.clear()
        googleMap.addMarker(
            MarkerOptions().position(campus.location).title(campus.name).snippet(campus.address)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campus.location, 15f))
    }
}

// Campus data class
data class Campus(
    val name: String,
    val location: LatLng,
    val address: String
)

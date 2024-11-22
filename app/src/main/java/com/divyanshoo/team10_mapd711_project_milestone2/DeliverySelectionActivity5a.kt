package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class DeliverySelectionActivity5a : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationSpinner: Spinner

    private val campusLocations = listOf(
        Campus(
            "Morningside",
            LatLng(43.78713095599446, -79.18935308080529),
            "755 Morningside Ave, Scarborough, ON M1C 4Z4\n8:30 a.m.–4:30 p.m.\n+1 416-289-5000\ncentennialcollege.ca"
        ),
        Campus(
            "Downsview",
            LatLng(43.74804023006045, -79.47487355889258),
            "65 Carl Hall Rd, Toronto, ON M3K 2C1\n8:30 a.m.–4:30 p.m.\n+1 416-289-5000\ncentennialcollege.ca"
        ),
        Campus(
            "Ashtonbee",
            LatLng(43.730346082748056, -79.29202483005702),
            "75 Ashtonbee Rd, Scarborough, ON M1L 4C9\n8:30 a.m.–4:30 p.m.\n+1 416-289-5000\ncentennialcollege.ca"
        ),
        Campus(
            "Progress",
            LatLng(43.78538241881133, -79.22812618772701),
            "941 Progress Ave, Scarborough, ON M1G 3T8\n8:30 a.m.–4:30 p.m.\n+1 416-289-5000\ncentennialcollege.ca"
        ),
        Campus(
            "SAC",
            LatLng(43.69026700487268, -79.34687459617166),
            "951 Carlaw Ave, Toronto, ON M4K 3M2\n8:30 a.m.–4:30 p.m.\n+1 416-289-5000\ncentennialcollege.ca"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_selection_activity5a)

        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"

        val deliveryOptions = arrayOf("Door Delivery", "Campus Pickup")
        val deliverySpinner: Spinner = findViewById(R.id.deliverySpinner)

        // Custom ArrayAdapter with custom item layout for the selected item
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item, // Custom layout for the selected item (white text)
            deliveryOptions
        )
        // Use the default system dropdown layout for the dropdown items (black text)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Default dropdown layout
        deliverySpinner.adapter = adapter

        val mapFragmentContainer = findViewById<FrameLayout>(R.id.mapFragmentContainer)
        locationSpinner = findViewById(R.id.campusSpinner)
        val proceedButton: Button = findViewById(R.id.proceedButton)
        val textViewCampus = findViewById<TextView>(R.id.textViewCampus)

        mapFragmentContainer.visibility = View.GONE
        locationSpinner.visibility = View.GONE
        textViewCampus.visibility = View.GONE

        deliverySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (deliveryOptions[position] == "Campus Pickup") {
                    mapFragmentContainer.visibility = View.VISIBLE
                    locationSpinner.visibility = View.VISIBLE
                    textViewCampus.visibility = View.VISIBLE

                    locationSpinner.adapter = ArrayAdapter(
                        this@DeliverySelectionActivity5a,
                        R.layout.spinner_item,  // Reference custom spinner layout for campuses
                        campusLocations.map { it.name }
                    ).apply {
                        setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item) // Custom dropdown
                    }

                    // Initialize map fragment if not already done
                    val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragmentContainer)
                            as? SupportMapFragment ?: SupportMapFragment.newInstance().also {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mapFragmentContainer, it)
                            .commit()
                        it.getMapAsync(this@DeliverySelectionActivity5a)
                    }
                } else {
                    mapFragmentContainer.visibility = View.GONE
                    locationSpinner.visibility = View.GONE
                    textViewCampus.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        proceedButton.setOnClickListener {
            val selectedDeliveryOption = deliverySpinner.selectedItem.toString()
            val intent = Intent(this, CustomerInfoActivity6::class.java).apply {
                putExtra("SELECTED_BRAND", selectedBrand)
                putExtra("SELECTED_MODEL", selectedModel)
                putExtra("SELECTED_PRICE", selectedPrice)
                putExtra("SELECTED_STORAGE", selectedStorage)
                putExtra("SELECTED_COLOR", selectedColor)
                putExtra("DELIVERY_TYPE", selectedDeliveryOption)

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

        // Set the default camera position to the Morningside campus
        val morningsideCampus = campusLocations[0]
        googleMap.clear()
        googleMap.addMarker(
            MarkerOptions()
                .position(morningsideCampus.location)
                .title(morningsideCampus.name)
                .snippet(morningsideCampus.address)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(morningsideCampus.location, 15f))

        // Set custom InfoWindowAdapter to display full address
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                // No need for custom content, use default window
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val context = applicationContext
                val linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL

                val titleTextView = TextView(context)
                titleTextView.text = marker.title
                titleTextView.setTextColor(Color.BLACK)
                titleTextView.textSize = 16f
                linearLayout.addView(titleTextView)

                val snippetTextView = TextView(context)
                snippetTextView.text = marker.snippet // Full address from the marker
                snippetTextView.setTextColor(Color.BLACK)
                snippetTextView.textSize = 14f
                snippetTextView.setPadding(0, 5, 0, 0)
                linearLayout.addView(snippetTextView)

                return linearLayout
            }
        })

        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateMap(campusLocations[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateMap(campus: Campus) {
        if (::googleMap.isInitialized) {
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions()
                    .position(campus.location)
                    .title(campus.name)
                    .snippet(campus.address)
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campus.location, 15f))
        }
    }


    data class Campus(
        val name: String,
        val location: LatLng,
        val address: String
    )
}

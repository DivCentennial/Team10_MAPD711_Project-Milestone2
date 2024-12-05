//DeliverySelectionActivity5a
// User can select the Campus Pickup or the home delivery,if campus selected then map is shown if home then not shown.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
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
    private lateinit var satelliteSwitch: Switch  // Satellite view

    private val campusLocations = listOf(
        Campus("Morningside", LatLng(43.78713095599446, -79.18935308080529), "755 Morningside Ave"),
        Campus("Downsview", LatLng(43.74804023006045, -79.47487355889258), "65 Carl Hall Rd"),
        Campus("Ashtonbee", LatLng(43.730346082748056, -79.29202483005702), "75 Ashtonbee Rd"),
        Campus("Progress", LatLng(43.78538241881133, -79.22812618772701), "941 Progress Ave"),
        Campus("SAC", LatLng(43.69026700487268, -79.34687459617166), "951 Carlaw Ave")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_selection_activity5a)

        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"

// Log the data to check if it's passed correctly
        android.util.Log.d("DeliverySelection", "Brand: $selectedBrand")
        android.util.Log.d("DeliverySelection", "Model: $selectedModel")
        android.util.Log.d("DeliverySelection", "Storage: $selectedStorage")
        android.util.Log.d("DeliverySelection", "Color: $selectedColor")
        android.util.Log.d("DeliverySelection", "Price: $selectedPrice")


        val deliveryOptions = arrayOf("Door Delivery", "Order Pickup")
        val deliverySpinner: Spinner = findViewById(R.id.deliverySpinner)

        // Spinner adapter
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

        // Visibility settings
        mapFragmentContainer.visibility = View.GONE
        locationSpinner.visibility = View.GONE
        textViewCampus.visibility = View.GONE
        satelliteSwitch.visibility = View.GONE

        deliverySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (deliveryOptions[position] == "Order Pickup") {
                    mapFragmentContainer.visibility = View.VISIBLE
                    locationSpinner.visibility = View.VISIBLE
                    textViewCampus.visibility = View.VISIBLE
                    satelliteSwitch.visibility = View.VISIBLE

                    locationSpinner.adapter = ArrayAdapter(
                        this@DeliverySelectionActivity5a,
                        R.layout.spinner_item,
                        campusLocations.map { it.name }
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }

                    // Initialize map fragment
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
                    val selectedCampus = campusLocations[locationSpinner.selectedItemPosition]
                    putExtra("CAMPUS_NAME", selectedCampus.name)
                    putExtra("CAMPUS_ADDRESS", selectedCampus.address)
                }
            }
            startActivity(intent)
        }

        satelliteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (::googleMap.isInitialized) {
                googleMap.mapType = if (isChecked) GoogleMap.MAP_TYPE_SATELLITE else GoogleMap.MAP_TYPE_NORMAL
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val morningsideCampus = campusLocations[0]
        googleMap.clear()
        googleMap.addMarker(
            MarkerOptions()
                .position(morningsideCampus.location)
                .title(morningsideCampus.name)
                .snippet(morningsideCampus.address)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(morningsideCampus.location, 15f))

        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? = null
            override fun getInfoContents(marker: Marker): View? {
                val context = applicationContext
                val linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL

                val titleTextView = TextView(context)
                titleTextView.text = marker.title
                titleTextView.setTextColor(Color.BLACK)
                linearLayout.addView(titleTextView)

                val snippetTextView = TextView(context)
                snippetTextView.text = marker.snippet
                snippetTextView.setTextColor(Color.BLACK)
                linearLayout.addView(snippetTextView)

                return linearLayout
            }
        })

        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val campus = campusLocations[position]
                googleMap.clear()
                googleMap.addMarker(
                    MarkerOptions()
                        .position(campus.location)
                        .title(campus.name)
                        .snippet(campus.address)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campus.location, 15f))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    data class Campus(val name: String, val location: LatLng, val address: String)
}

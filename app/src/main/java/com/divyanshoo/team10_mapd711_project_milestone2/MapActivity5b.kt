package com.divyanshoo.team10_mapd711_project_milestone2

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.appcompat.app.AppCompatActivity

class MapActivity5b : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationSpinner: Spinner

    // Campus data
    private val campusLocations = listOf(
        Campus("Morningside", LatLng(43.78713095599446, -79.18935308080529), "755 Morningside Ave, Scarborough, ON M1C 4Z4,8:30a.m.–4:30p.m., +14162895000, centennialcollege.ca"),
        Campus("Downsview", LatLng(43.74804023006045, -79.47487355889258), "65 Carl Hall Rd, Toronto, ON M3K 2C1, 8:30a.m.–4:30p.m,+14162895000, centennialcollege.ca"),
        Campus("Ashtonbee", LatLng(43.730346082748056, -79.29202483005702), "75 Ashtonbee Rd, Scarborough, ON M1L 4C9, 8:30a.m.–4:30p.m,+14162895000, centennialcollege.ca"),
        Campus("Progress", LatLng(43.78538241881133, -79.22812618772701), "941 Progress Ave, Scarborough, ON M1G 3T8, 8:30a.m.–4:30p.m,+14162895000, centennialcollege.ca"),
        Campus("SAC", LatLng(43.69026700487268, -79.34687459617166), "951 Carlaw Ave, Toronto, ON M4K 3M2, 8:30a.m.–4:30p.m,+14162895000, centennialcollege.ca")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_activity5b)

        // Initialize spinner
        locationSpinner = findViewById(R.id.campusSpinner)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            campusLocations.map { it.name } // Campus names for the spinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locationSpinner.adapter = adapter

        // Set up map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Spinner selection listener
        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                val selectedCampus = campusLocations[position]
                updateMap(selectedCampus)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        // Load the first campus by default
        updateMap(campusLocations[0])
    }

    private fun updateMap(campus: Campus) {
        // Clear previous markers
        googleMap.clear()

        // Add marker and move camera
        val location = campus.location
        googleMap.addMarker(
            MarkerOptions().position(location).title(campus.name).snippet(campus.address)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}

// Campus data class
data class Campus(
    val name: String,
    val location: LatLng,
    val address: String
)

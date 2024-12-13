// DeliverySelectionViewModel.kt
package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.app.Application
import android.widget.AdapterView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.divyanshoo.team10_mapd711_project_milestone2.view.DeliverySelectionActivity5a
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class DeliverySelectionViewModel(application: Application) : AndroidViewModel(application) {

    val selectedBrand = MutableLiveData<String>()
    val selectedModel = MutableLiveData<String>()
    val selectedStorage = MutableLiveData<String>()
    val selectedColor = MutableLiveData<String>()
    val selectedPrice = MutableLiveData<String>()
    val campusLocations = MutableLiveData<List<Campus>>()
    val selectedDeliveryOption = MutableLiveData<String>()

    init {
        // Initialize the campus list and other necessary data
        campusLocations.value = listOf(
            Campus("Morningside", LatLng(43.78713095599446, -79.18935308080529), "755 Morningside Ave"),
            Campus("Downsview", LatLng(43.74804023006045, -79.47487355889258), "65 Carl Hall Rd"),
            Campus("Ashtonbee", LatLng(43.730346082748056, -79.29202483005702), "75 Ashtonbee Rd"),
            Campus("Progress", LatLng(43.78538241881133, -79.22812618772701), "941 Progress Ave"),
            Campus("SAC", LatLng(43.69026700487268, -79.34687459617166), "951 Carlaw Ave")
        )
    }

    // Method to update the map based on selected campus
    fun updateCampusLocationOnMap(map: GoogleMap, campusPosition: Int) {
        val campus = campusLocations.value?.get(campusPosition)
        campus?.let {
            map.clear()
            map.addMarker(MarkerOptions().position(it.location).title(it.name).snippet(it.address))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it.location, 15f))
        }
    }

    // Update delivery option selection
    fun onDeliveryOptionSelected(position: Int) {
        selectedDeliveryOption.value = if (position == 0) "Door Delivery" else "Order Pickup"
    }

    // Update satellite view based on user input
    fun updateSatelliteView(isChecked: Boolean, googleMap: GoogleMap) {
        googleMap.mapType = if (isChecked) GoogleMap.MAP_TYPE_SATELLITE else GoogleMap.MAP_TYPE_NORMAL
    }

    // Data class to represent campus
    data class Campus(val name: String, val location: LatLng, val address: String)
}

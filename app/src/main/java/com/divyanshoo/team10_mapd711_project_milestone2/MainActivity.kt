/*
Name: - Divyanshoo Sinha (301486627)
      - Kashish Pramod Yadav (301485842)

Milestone 3
Group Project

Team 10

Overall Description: This milestone 3 we integrate google maps in our project from milestone 2 and user can select campus or door delivery.
in campus pickup user can select among 5 locations and based on that the map location is changed.
*/

//MainActivity.kt
// View Description: This view is the opening view with the app logo and a button to order a phone.
package com.divyanshoo.team10_mapd711_project_milestone2
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)   //activity_main.xml is the view for this activity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

val orderButton = findViewById<Button>(R.id.button2)  // When you hit the button it will navigate to BrandSelectionActivity2.kt
        orderButton.setOnClickListener {
            val intent = Intent(this, BrandSelectionActivity2::class.java)
            startActivity(intent)
        }


    }
}
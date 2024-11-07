/*
Name: - Divyanshoo Sinha (301486627)
      - Kashish Pramod Yadav (301485842)

Milestone 2
Group Project

Team 10

 Overall Description: This milestone 2 we make a mobile shopping app where we can shop different brands with their respective models.
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
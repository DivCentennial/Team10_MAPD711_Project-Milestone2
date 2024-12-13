package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.OrderHistoryViewModel
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.OrderHistoryViewModelFactory

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels {
        OrderHistoryViewModelFactory(sharedPreferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Observe LiveData from ViewModel
        orderHistoryViewModel.orderDetails.observe(this) { orderDetails ->
            val orderHistoryTextView: TextView = findViewById(R.id.orderHistoryTextView)
            orderHistoryTextView.text = orderDetails
        }

        orderHistoryViewModel.orderStatus.observe(this) { orderStatus ->
            val orderStatusTextView: TextView = findViewById(R.id.orderStatusTextView)
            orderStatusTextView.text = orderStatus
        }

        orderHistoryViewModel.canCancelOrder.observe(this) { canCancel ->
            val cancelOrderButton: Button = findViewById(R.id.cancelOrderButton)
            cancelOrderButton.visibility = if (canCancel) View.VISIBLE else View.GONE
        }

        // Load order details when the activity is created
        orderHistoryViewModel.loadOrderDetails()

        // Cancel Order Button
        val cancelOrderButton: Button = findViewById(R.id.cancelOrderButton)
        cancelOrderButton.setOnClickListener {
            // Call ViewModel to cancel the order
            orderHistoryViewModel.cancelOrder()

            // Show confirmation or notify the user
            Toast.makeText(this, "Order cancelled successfully", Toast.LENGTH_SHORT).show()
        }
    }
}

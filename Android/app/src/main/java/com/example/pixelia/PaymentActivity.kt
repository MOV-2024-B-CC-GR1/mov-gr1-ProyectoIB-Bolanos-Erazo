package com.example.pixelia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pixelia.model.Purchase
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PaymentActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance("https://pixelia-e12f8-default-rtdb.firebaseio.com/")
    private val purchasesRef = database.getReference("comprados")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val gameId = intent.getStringExtra("gameId") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val gameTitle = intent.getStringExtra("title") ?: ""
        val gamePrice = intent.getStringExtra("price") ?: ""

        // Cargar la imagen usando Glide
        findViewById<ImageView>(R.id.gameImagePayment).also { imageView ->
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)
        }

        findViewById<TextView>(R.id.gameTitlePayment).text = gameTitle

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.confirmPaymentButton).setOnClickListener {
            registerPurchase(gameId, gamePrice)
        }
    }

    private fun registerPurchase(gameId: String, price: String) {
        val currentDateTime = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        val purchase = Purchase(
            gameId = gameId,
            userId = "ELiasLBT1",
            purchaseDate = currentDateTime,
            price = price
        )

        purchasesRef.push().setValue(purchase)
            .addOnSuccessListener {
                startActivity(Intent(this, SuccessPaymentActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al registrar la compra: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
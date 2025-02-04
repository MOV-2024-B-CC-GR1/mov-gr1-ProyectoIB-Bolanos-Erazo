package com.example.pixelia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        val gameImage = findViewById<ImageView>(R.id.gameImage)
        val gameTitle = findViewById<TextView>(R.id.gameTitle)
        val gameDescription = findViewById<TextView>(R.id.gameDescription)
        val gamePrice = findViewById<TextView>(R.id.gamePrice)
        val buyButton = findViewById<Button>(R.id.buyButton)

        // Obtener datos del Intent
        val intent = intent
        val imageResource = intent.getIntExtra("imageResource", 0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")

        // Configurar los datos en la UI
        gameImage.setImageResource(imageResource)
        gameTitle.text = title
        gameDescription.text = description
        gamePrice.text = price

        // Configurar el botón de compra
        buyButton.setOnClickListener {
            val paymentIntent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("imageResource", imageResource)
                putExtra("title", title)
                putExtra("price", price)
            }
            startActivity(paymentIntent)
        }
    }
}
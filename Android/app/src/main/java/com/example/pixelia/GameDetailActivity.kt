package com.example.pixelia

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class GameDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        // Obtener las vistas
        val gameImage = findViewById<ImageView>(R.id.gameImage)
        val gameTitle = findViewById<TextView>(R.id.gameTitle)
        val gameDescription = findViewById<TextView>(R.id.gameDescription)
        val gamePrice = findViewById<TextView>(R.id.gamePrice)
        val buyButton = findViewById<Button>(R.id.buyButton)

        // Obtener los datos del Intent
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val price = intent.getStringExtra("price") ?: ""
        val gameId = intent.getStringExtra("gameId") ?: ""

        // Cargar la imagen usando Glide
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(gameImage)

        // Establecer los textos
        gameTitle.text = title
        gameDescription.text = description
        gamePrice.text = price

        // Configurar el botón de compra
        buyButton.setOnClickListener {
            val paymentIntent = android.content.Intent(this, PaymentActivity::class.java).apply {
                putExtra("gameId", gameId)
                putExtra("imageUrl", imageUrl)
                putExtra("title", title)
                putExtra("price", price)
            }
            startActivity(paymentIntent)
        }
    }
}
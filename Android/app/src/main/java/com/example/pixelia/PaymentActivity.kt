package com.example.pixelia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Obtener datos del juego del intent
        val gameImage = intent.getIntExtra("imageResource", 0)
        val gameTitle = intent.getStringExtra("title")

        // Configurar la imagen y título del juego
        findViewById<ImageView>(R.id.gameImagePayment).setImageResource(gameImage)
        findViewById<TextView>(R.id.gameTitlePayment).text = gameTitle

        // Configurar botones
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish() // Regresa a la pantalla anterior
        }

        findViewById<Button>(R.id.confirmPaymentButton).setOnClickListener {
            // Aquí puedes agregar validación de los campos si lo deseas
            startActivity(Intent(this, SuccessPaymentActivity::class.java))
            finish()
        }
    }
}
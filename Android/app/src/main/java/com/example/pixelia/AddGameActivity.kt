package com.example.pixelia

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pixelia.model.Game
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddGameActivity : BaseActivity() {
    private val database = FirebaseDatabase.getInstance("https://pixelia-e12f8-default-rtdb.firebaseio.com/")
    private val gamesRef = database.getReference("juegos")

    override fun getLayoutResourceId(): Int = R.layout.activity_add_game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        val titleInput = findViewById<TextInputEditText>(R.id.titleInput)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descriptionInput)
        val priceInput = findViewById<TextInputEditText>(R.id.priceInput)
        val imageUrlInput = findViewById<TextInputEditText>(R.id.imageUrlInput)
        val saveButton = findViewById<Button>(R.id.saveGameButton)

        saveButton.setOnClickListener {
            val title = titleInput.text?.toString()?.trim() ?: ""
            val description = descriptionInput.text?.toString()?.trim() ?: ""
            val priceText = priceInput.text?.toString()?.trim() ?: ""
            val imageUrl = imageUrlInput.text?.toString()?.trim() ?: ""

            if (title.isEmpty() || description.isEmpty() || priceText.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val gameId = UUID.randomUUID().toString()
                val price = if (priceText.startsWith("$")) priceText else "$$priceText"

                val game = Game(
                    id = gameId,
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    price = price
                )

                saveButton.isEnabled = false // Deshabilitar el botón mientras se guarda

                gamesRef.child(gameId).setValue(game)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Juego guardado exitosamente", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Log.e("AddGameActivity", "Error al guardar el juego", e)
                        Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
                        saveButton.isEnabled = true // Re-habilitar el botón
                    }

            } catch (e: Exception) {
                Log.e("AddGameActivity", "Error al procesar los datos", e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                saveButton.isEnabled = true
            }
        }
    }
}
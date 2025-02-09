package com.example.pixelia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelia.adapter.GameAdapter
import com.example.pixelia.model.Game
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : BaseActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter
    private val database = FirebaseDatabase.getInstance("https://pixelia-e12f8-default-rtdb.firebaseio.com/")
    private val gamesRef = database.getReference("juegos")

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GameAdapter(this, mutableListOf())
        recyclerView.adapter = adapter

        loadGames()
    }

    private fun loadGames() {
        try {
            gamesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val gamesList = mutableListOf<Game>()
                        for (gameSnapshot in snapshot.children) {
                            val game = gameSnapshot.getValue(Game::class.java)
                            game?.let { gamesList.add(it) }
                        }
                        adapter.updateGames(gamesList.sortedBy { it.title })
                        Log.d("MainActivity", "Juegos cargados: ${gamesList.size}")
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error al procesar los datos", e)
                        Toast.makeText(this@MainActivity,
                            "Error al procesar los juegos: ${e.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainActivity", "Error de Firebase: ${error.message}",
                        error.toException())
                    Toast.makeText(this@MainActivity,
                        "Error al cargar los juegos: ${error.message}",
                        Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al configurar el listener", e)
            Toast.makeText(this,
                "Error al conectar con la base de datos: ${e.message}",
                Toast.LENGTH_LONG).show()
        }
    }
}
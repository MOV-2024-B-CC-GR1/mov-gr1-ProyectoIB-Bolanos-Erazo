package com.example.pixelia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelia.adapter.PurchasedGamesAdapter
import com.example.pixelia.model.Game
import com.example.pixelia.model.Purchase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyGamesActivity : BaseActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PurchasedGamesAdapter
    private val database = FirebaseDatabase.getInstance("https://pixelia-e12f8-default-rtdb.firebaseio.com/")

    override fun getLayoutResourceId(): Int = R.layout.activity_my_games

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = findViewById(R.id.recyclerViewMyGames)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = PurchasedGamesAdapter(this) { game ->
            showExecuteGameDialog(game)
        }
        recyclerView.adapter = adapter

        loadPurchasedGames()
    }

    private fun loadPurchasedGames() {
        val userId = "ELiasLBT1" // Usuario actual
        val purchasesRef = database.getReference("comprados")
        val gamesRef = database.getReference("juegos")

        purchasesRef.orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(purchasesSnapshot: DataSnapshot) {
                    val purchasedGameIds = purchasesSnapshot.children.mapNotNull {
                        it.getValue(Purchase::class.java)?.gameId
                    }

                    gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(gamesSnapshot: DataSnapshot) {
                            val purchasedGames = mutableListOf<Game>()
                            for (gameId in purchasedGameIds) {
                                gamesSnapshot.child(gameId).getValue(Game::class.java)?.let {
                                    purchasedGames.add(it)
                                }
                            }
                            adapter.submitList(purchasedGames)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@MyGamesActivity,
                                "Error al cargar los juegos", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MyGamesActivity,
                        "Error al cargar las compras", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showExecuteGameDialog(game: Game) {
        AlertDialog.Builder(this)
            .setTitle("Ejecutar Juego")
            .setMessage("¿Deseas ejecutar ${game.title}?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
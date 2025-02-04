package com.example.pixelia

import android.os.Bundle
import android.widget.Toast // Agregar este import
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelia.adapter.GameAdapter
import com.example.pixelia.model.Game

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter
    private lateinit var gameList: MutableList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            gameList = mutableListOf()
            gameList.add(Game(
                "Game 1",
                "Description of Game 1",
                R.drawable.game_image_1,
                "$4.99"
            ))
            gameList.add(Game(
                "Game 2",
                "Description of Game 2",
                R.drawable.game_image_2,
                "$9.99"
            ))
            gameList.add(Game(
                "Game 3",
                "Description of Game 3",
                R.drawable.game_image_3,
                "$14.99"
            ))

            adapter = GameAdapter(this, gameList)
            recyclerView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this, "Error al inicializar la aplicación", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
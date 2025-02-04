package com.example.pixelia.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast // Agregar este import
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelia.GameDetailActivity
import com.example.pixelia.R
import com.example.pixelia.model.Game

class GameAdapter(private val context: Context, private val games: List<Game>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.title.text = game.title
        holder.description.text = game.description
        holder.image.setImageResource(game.imageResource)

        holder.itemView.setOnClickListener {
            try {
                val intent = Intent(context, GameDetailActivity::class.java).apply {
                    putExtra("imageResource", game.imageResource)
                    putExtra("title", game.title)
                    putExtra("description", game.description)
                    putExtra("price", game.price)
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al abrir los detalles del juego", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int = games.size

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
    }
}
package com.example.pixelia.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixelia.GameDetailActivity
import com.example.pixelia.R
import com.example.pixelia.model.Game

class GameAdapter(
    private val context: Context,
    private var games: MutableList<Game>
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        // Configurar título
        holder.title.text = game.title

        // Configurar descripción
        holder.description.text = game.description

        // Configurar precio
        holder.price.text = game.price

        // Cargar imagen usando Glide
        Glide.with(context)
            .load(game.imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // Imagen por defecto mientras carga
            .error(R.drawable.ic_launcher_background) // Imagen si hay error
            .centerCrop()
            .into(holder.image)

        // Configurar click en la tarjeta
        holder.itemView.setOnClickListener {
            val intent = Intent(context, GameDetailActivity::class.java).apply {
                putExtra("gameId", game.id)
                putExtra("title", game.title)
                putExtra("description", game.description)
                putExtra("imageUrl", game.imageUrl)
                putExtra("price", game.price)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = games.size

    fun updateGames(newGames: List<Game>) {
        games.clear()
        games.addAll(newGames)
        notifyDataSetChanged()
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val price: TextView = itemView.findViewById(R.id.price)
    }
}
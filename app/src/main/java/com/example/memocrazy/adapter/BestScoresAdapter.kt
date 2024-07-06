package com.example.memocrazy.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore

// Adaptador para mostrar una lista de BestScore en un RecyclerView
class BestScoresAdapter(private val items: MutableList<BestScore>) : RecyclerView.Adapter<BestScoreViewHolder>() {

    // Crea y retorna un nuevo ViewHolder basado en el layout item_best_score
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_best_score, parent, false)
        return BestScoreViewHolder(itemView)
    }

    // Retorna la cantidad de elementos en la lista items
    override fun getItemCount(): Int = items.size

    // Asocia los datos de un elemento en la posición dada con el ViewHolder
    override fun onBindViewHolder(holder: BestScoreViewHolder, position: Int) {
        // Registro de depuración para mostrar la posición del elemento actual
        Log.d("Position", "Ojo: $position")

        // Obtiene el BestScore en la posición 'position'
        val item = items[position]

        // Llama al método render del ViewHolder para mostrar los datos en la vista correspondiente
        holder.render(item)

        // Notifica cambios en los datos (descomentar para actualizar la vista completa cuando cambian los datos)
        // notifyDataSetChanged()
    }
}

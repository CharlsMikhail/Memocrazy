package com.example.memocrazy.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.Theme

/**
 * Adaptador para mostrar una lista de temas (Theme) en un RecyclerView.
 *
 * @param items Lista mutable de temas a mostrar.
 * @param onItemSelected Función lambda que se llama cuando se selecciona un tema.
 */
class ThemeAdapter(private val items: MutableList<Theme>, val onItemSelected: (Theme) -> Unit) : RecyclerView.Adapter<ThemeViewHolder>() {

    // Crea y retorna un nuevo ViewHolder basado en el layout item_theme
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false)
        return ThemeViewHolder(itemView)
    }

    // Retorna la cantidad de elementos en la lista items
    override fun getItemCount(): Int = items.size

    // Asocia los datos de un elemento en la posición dada con el ViewHolder
    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {

        // Obtiene el Theme en la posición 'position'
        val item = items[position]

        // Llama al método render del ViewHolder para mostrar los datos en la vista correspondiente
        holder.render(item, onItemSelected)

    }
}

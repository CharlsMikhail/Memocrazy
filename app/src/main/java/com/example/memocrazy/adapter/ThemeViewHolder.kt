package com.example.memocrazy.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.Theme

/**
 * ViewHolder para mostrar un elemento Theme en un RecyclerView.
 *
 * @param itemView Vista raíz del elemento de la lista.
 */
class ThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Referencias a las vistas dentro del layout item_theme
    private val viewThemeName = itemView.findViewById<TextView>(R.id.theme_name)
    private val viewThemeIcon = itemView.findViewById<ImageView>(R.id.img_theme)

    /**
     * Método para asignar los datos de un Theme a las vistas correspondientes.
     *
     * @param item Objeto Theme que contiene los datos a mostrar.
     * @param onItemSelected Función lambda que se llama cuando se selecciona un tema.
     */
    fun render(item: Theme, onItemSelected: (Theme) -> Unit) {
        // Asigna el nombre del tema al TextView theme_name
        viewThemeName.text = item.name

        // Asigna el icono del tema al ImageView img_theme
        viewThemeIcon.setImageResource(item.icon)

        // Configura el listener de clic en el itemView para llamar a onItemSelected cuando se selecciona el tema
        itemView.setOnClickListener { onItemSelected(item) }
    }

}

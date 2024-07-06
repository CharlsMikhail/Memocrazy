package com.example.memocrazy.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore

// ViewHolder para mostrar un elemento BestScore en un RecyclerView
class BestScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Referencias a las vistas dentro del layout item_best_score
    private val viewEmailUser = itemView.findViewById<TextView>(R.id.txt_email_user)
    private val viewScoreUser = itemView.findViewById<TextView>(R.id.txt_score_user)

    // Método para asignar los datos de un BestScore a las vistas correspondientes
    fun render(item: BestScore) {
        // Asigna el nombre del usuario al TextView txt_email_user
        viewEmailUser.text = item.name

        // Convierte el puntaje a String y lo asigna al TextView txt_score_user
        viewScoreUser.text = item.score.toString()
    }

}

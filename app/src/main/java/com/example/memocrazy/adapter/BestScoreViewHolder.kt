package com.example.memocrazy.adapter


import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore

class BestScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewNombre = itemView.findViewById<TextView>(R.id.txt_name_user)
    private val viewScore = itemView.findViewById<TextView>(R.id.txt_score)
    fun render(item: BestScore) {
        viewNombre.text = item.name
        viewScore.text = item.score.toString()
    }

}

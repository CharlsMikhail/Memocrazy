package com.example.memocrazy.adapter


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore

class BestScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewEmailUser = itemView.findViewById<TextView>(R.id.txt_email_user)
    private val viewScoreUser = itemView.findViewById<TextView>(R.id.txt_score_user)
    fun render(item: BestScore) {
        viewEmailUser.text = item.name
        viewScoreUser.text = item.score.toString()
    }

}

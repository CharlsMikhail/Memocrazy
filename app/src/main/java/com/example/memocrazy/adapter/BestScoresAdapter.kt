package com.example.memocrazy.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore
class BestScoresAdapter(private val items: MutableList<BestScore>): RecyclerView.Adapter<BestScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_best_score,parent, false)
        return BestScoreViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BestScoreViewHolder, position: Int) {
        Log.d("Position", "Ojo: $position")
        val item = items[position]
        holder.render(item)
        //notifyDataSetChanged()
    }
}
package com.example.memocrazy.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore
import com.example.memocrazy.entities.Theme

class ThemeAdapter(private val items: MutableList<Theme>, val onItemSelected: (Theme) -> Unit): RecyclerView.Adapter<ThemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_theme,parent, false)
        return ThemeViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        Log.d("Position", "Ojo: $position")
        val item = items[position]
        holder.render(item, onItemSelected)
        //notifyDataSetChanged()
    }
}
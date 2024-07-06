package com.example.memocrazy.adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.R
import com.example.memocrazy.entities.BestScore
import com.example.memocrazy.entities.Theme

class ThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewThemeName = itemView.findViewById<TextView>(R.id.theme_name)
    private val viewThemeIcon = itemView.findViewById<ImageView>(R.id.img_theme)
    fun render(item: Theme, onItemSelected: (Theme) -> Unit) {
        viewThemeName.text = item.name
        viewThemeIcon.setImageResource(item.icon)
        itemView.setOnClickListener { onItemSelected(item) }
    }

}

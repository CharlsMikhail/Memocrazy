package com.example.memocrazy

import android.os.Bundle
import android.view.View
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.memocrazy.adapter.GridAdapter

class GameFragment : Fragment(R.layout.fragment_game) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridView: GridView = view.findViewById(R.id.grid_game)
        gridView.adapter = GridAdapter(view.context)

    }
}
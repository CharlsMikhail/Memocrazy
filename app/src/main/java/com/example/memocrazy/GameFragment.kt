package com.example.memocrazy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.adapter.GridAdapter

class GameFragment : Fragment(R.layout.fragment_game) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)

        val gridView: GridView = view.findViewById(R.id.grid_game)
        gridView.adapter = GridAdapter(view.context)

    }

    private fun eventos(view: View) {
        val btnExit: Button = view.findViewById(R.id.btn_exit)

        btnExit.setOnClickListener {
            // Por el momento, en un futuro mostrará un diálogo de confirmación
            view.findNavController().popBackStack()
        }
    }
}
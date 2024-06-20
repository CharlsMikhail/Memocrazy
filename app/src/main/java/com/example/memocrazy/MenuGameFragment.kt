package com.example.memocrazy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class MenuGameFragment : Fragment(R.layout.fragment_menu_game) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventos(view)
    }

    private fun eventos(view: View) {
        val btnStarGame = view.findViewById<Button>(R.id.btn_start_game)

        btnStarGame.setOnClickListener {
            view.findNavController().navigate(R.id.action_menuGameFragment_to_gameFragment)
        }

        val btnScoreList = view.findViewById<Button>(R.id.btn_best_scores)

        btnScoreList.setOnClickListener {
            view.findNavController().navigate(R.id.action_menuGameFragment_to_bestScoresFragment)
        }
    }
}
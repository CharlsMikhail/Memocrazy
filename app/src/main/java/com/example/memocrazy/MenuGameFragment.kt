package com.example.memocrazy

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

/**
 * Fragmento que maneja la vista del menú del juego en Memocrazy.
 *
 * @property mediaPlayer Reproductor de música de fondo.
 */
class MenuGameFragment : Fragment(R.layout.fragment_menu_game) {

    private lateinit var mediaPlayer: MediaPlayer

    /**
     * Se llama cuando la vista del fragmento se crea. Configura los eventos de los botones
     * del menú del juego.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)
    }

    /**
     * Configura los eventos de clic para los botones del menú del juego.
     */
    private fun eventos(view: View) {
        val btnStarGame = view.findViewById<Button>(R.id.btn_start_game)

        btnStarGame.setOnClickListener {
            view.findNavController().navigate(R.id.action_menuGameFragment_to_themeFragment)
        }

        val btnScoreList = view.findViewById<Button>(R.id.btn_best_scores)

        btnScoreList.setOnClickListener {
            view.findNavController().navigate(R.id.action_menuGameFragment_to_bestScoresFragment)
        }
    }

}

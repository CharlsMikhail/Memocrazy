package com.example.memocrazy

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


/**
 * @problemDescription Se encarga de la vista del menu del juego, donde habra una bienvenida y se
 * mostrara las diferentes opciones del juego(Jugar, Ver puntajes y quitar sonido).
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class MenuGameFragment : Fragment(R.layout.fragment_menu_game) {

    private lateinit var mediaPlayer: MediaPlayer

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
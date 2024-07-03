package com.example.memocrazy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.utils.KEY_SCORE
import kotlin.properties.Delegates

/**
 * @problemDescription Se encarga de mostrar los resultados del juego al final de cada partida.
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class ScoreFragment : Fragment(R.layout.fragment_score) {

    private var puntuacion by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            puntuacion = it.getInt(KEY_SCORE, 0)
        }

        actualizarInterfaz(view)
        eventos(view)

    }

    private fun actualizarInterfaz(view: View) {
        val txtScore = view.findViewById<TextView>(R.id.txt_score)
        txtScore.text = puntuacion.toString()
    }

    private fun eventos(view: View) {
        val btnVolverAJugar = view.findViewById<Button>(R.id.btn_restart_game)

        btnVolverAJugar.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val btnVolverAlMenu = view.findViewById<Button>(R.id.btn_menu)
        btnVolverAlMenu.setOnClickListener {
            view.findNavController().popBackStack(R.id.menuGameFragment, false)
        }

    }
}
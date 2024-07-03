package com.example.memocrazy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.adapter.GridAdapter


/**
 * @problemDescription Se encargara de mostrar el tablero de juego e interactuar con el
 * usuario. Además mostrar el nombre del jugador, temporizadir y el puntaje actual del jugador.
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class GameFragment : Fragment(R.layout.fragment_game) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)

        val gridView: GridView = view.findViewById(R.id.grid_game)
        gridView.adapter = GridAdapter(view.context, view.findViewById(R.id.txt_score))
    }

    private fun eventos(view: View) {
        val btnExit: Button = view.findViewById(R.id.btn_exit)

        btnExit.setOnClickListener {
            // Por el momento, en un futuro mostrará un diálogo de confirmación
            view.findNavController().popBackStack()
        }
    }
}
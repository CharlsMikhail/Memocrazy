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
 * @lastModification 06/07/24
 */
class GameFragment : Fragment(R.layout.fragment_game) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos(view)

        val mGridItems = arrayListOf(
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_manage,
            android.R.drawable.ic_menu_search,
            android.R.drawable.ic_menu_send,
            android.R.drawable.ic_menu_view,
            android.R.drawable.ic_menu_zoom,
            android.R.drawable.ic_media_ff,
            android.R.drawable.ic_media_next,
            android.R.drawable.ic_media_pause,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_manage,
            android.R.drawable.ic_menu_search,
            android.R.drawable.ic_menu_send,
            android.R.drawable.ic_menu_view,
            android.R.drawable.ic_menu_zoom,
            android.R.drawable.ic_media_ff,
            android.R.drawable.ic_media_next,
            android.R.drawable.ic_media_pause
        )

        val gridView: GridView = view.findViewById(R.id.grid_game)
        gridView.adapter = GridAdapter(view.context, view.findViewById(R.id.txt_score), view.findViewById(R.id.txt_time), mGridItems, "Pedro")
    }

    private fun eventos(view: View) {
        val btnExit: Button = view.findViewById(R.id.btn_exit)

        btnExit.setOnClickListener {
            // Por el momento, en un futuro mostrará un diálogo de confirmación
            view.findNavController().popBackStack()
        }
    }
}
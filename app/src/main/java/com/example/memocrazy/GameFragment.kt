package com.example.memocrazy

import ImagesProvider
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.adapter.GridAdapter
import com.example.memocrazy.utils.KEY_THEME
import com.google.firebase.auth.FirebaseAuth

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

        var theme = 0

        arguments?.let {
            theme = it.getInt(KEY_THEME)
        }
        actualizarInterfaz(view)
        eventos(view)

        val mGridItems = ImagesProvider.getCardsForTheme(theme)
        val gridView: GridView = view.findViewById(R.id.grid_game)
        gridView.adapter = GridAdapter(view.context, view.findViewById(R.id.txt_score), view.findViewById(R.id.txt_time), mGridItems, "Pedro")
    }

    private fun actualizarInterfaz(view: View) {
        val userName: TextView = view.findViewById(R.id.txt_player)

        // Obtenemos el usuario actualmente autenticado
        val user = FirebaseAuth.getInstance().currentUser

        // Verificamos que el usuario no sea nulo
        user?.let {
            // Obtenemos el email del usuario
            val email = user.email

            // Verificamos que el email no sea nulo y contiene el símbolo '@'
            email?.let {
                // Obtenemos la parte antes del '@'
                val userNameBeforeAt = email.substringBefore('@')

                // Actualizamos la interfaz con el nombre de usuario antes del '@'
                userName.text = "Player: $userNameBeforeAt"
            }
        }
    }

    private fun eventos(view: View) {
        val btnExit: Button = view.findViewById(R.id.btn_exit)

        btnExit.setOnClickListener {
            // Por el momento, en un futuro mostrará un diálogo de confirmación
            view.findNavController().popBackStack()
            view.findNavController().popBackStack()
        }
    }
}
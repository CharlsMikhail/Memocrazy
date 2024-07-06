package com.example.memocrazy

import ImagesProvider
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.adapter.GridAdapter
import com.example.memocrazy.utils.KEY_THEME
import com.google.firebase.auth.FirebaseAuth

/**
 * Fragmento que muestra el tablero de juego y permite interactuar con el usuario.
 *
 * @property theme Tema seleccionado para el juego.
 */
class GameFragment : Fragment(R.layout.fragment_game) {

    /**
     * Se llama cuando la vista del fragmento ha sido creada. Configura el tablero de juego.
     *
     * @param view Vista raíz del fragmento.
     * @param savedInstanceState Estado anterior del fragmento, si existe.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var theme = 0

        // Obtener el tema seleccionado desde los argumentos
        arguments?.let {
            theme = it.getInt(KEY_THEME)
        }

        // Actualizar la interfaz con el nombre de usuario y configurar eventos
        actualizarInterfaz(view)
        eventos(view)

        // Obtener las cartas para el tema seleccionado desde ImagesProvider
        val mGridItems = ImagesProvider.getCardsForTheme(theme)

        // Configurar el adaptador para el GridView que muestra el tablero de juego
        val gridView: GridView = view.findViewById(R.id.grid_game)
        gridView.adapter = GridAdapter(view.context, view.findViewById(R.id.txt_score), view.findViewById(R.id.txt_time), mGridItems, "Pedro")
    }

    /**
     * Actualiza la interfaz con el nombre de usuario actualmente autenticado.
     *
     * @param view Vista raíz del fragmento.
     */
    private fun actualizarInterfaz(view: View) {
        val userName: TextView = view.findViewById(R.id.txt_player)

        // Obtener el usuario actualmente autenticado
        val user = FirebaseAuth.getInstance().currentUser

        // Verificar que el usuario no sea nulo
        user?.let {
            // Obtener el email del usuario
            val email = user.email

            // Verificar que el email no sea nulo y contiene el símbolo '@'
            email?.let {
                // Obtener la parte antes del '@'
                val userNameBeforeAt = email.substringBefore('@')

                // Actualizar la interfaz con el nombre de usuario antes del '@'
                userName.text = "Player: $userNameBeforeAt"
            }
        }
    }

    /**
     * Configura los eventos para los botones y elementos interactivos del fragmento.
     *
     * @param view Vista raíz del fragmento.
     */
    private fun eventos(view: View) {
        val btnExit: Button = view.findViewById(R.id.btn_exit)

        // Configurar el evento clic para el botón de salida
        btnExit.setOnClickListener {
            // En el futuro, mostrar un diálogo de confirmación para salir del juego
            view.findNavController().popBackStack()
            view.findNavController().popBackStack()
        }
    }

    /**
     * Configura un callback para manejar el botón de retroceso del dispositivo.
     *
     * @param context Contexto actual del fragmento.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Crear un callback para manejar el botón de retroceso del dispositivo
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Mostrar un mensaje Toast indicando que no se puede regresar
                Toast.makeText(context, "No se puede regresar, abandone el juego", Toast.LENGTH_SHORT).show()
            }
        }

        // Agregar el callback al dispatcher del botón de retroceso
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }
}

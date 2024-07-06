package com.example.memocrazy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.utils.KEY_SCORE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

/**
 * Fragmento que muestra y gestiona los puntajes de los jugadores.
 *
 * @property score Puntaje actual del jugador.
 * @property db Instancia de FirebaseFirestore para acceder a la base de datos Firestore.
 * @property auth Instancia de FirebaseAuth para autenticación.
 */
class ScoreFragment : Fragment(R.layout.fragment_score) {

    private var score by Delegates.notNull<Int>()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    /**
     * Método llamado cuando la vista del fragmento se ha creado.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el puntaje del argumento pasado desde otro fragmento
        arguments?.let {
            score = it.getInt(KEY_SCORE, 0)
        }

        // Inicializar Firebase Firestore y FirebaseAuth
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Obtener el usuario actualmente autenticado
        val user = auth.currentUser
        if (user != null) {
            val email = user.email ?: ""

            // Actualizar la interfaz de usuario con el puntaje
            actualizarInterfaz(view)

            // Actualizar el puntaje en Firebase Firestore
            actualizarPuntuacionEnFirebase(email)
        } else {
            Log.w("auth_error", "No user is currently signed in")
        }

        // Configurar los eventos de los botones en la vista
        configurarEventos(view)
    }

    /**
     * Actualiza la interfaz de usuario con el puntaje actual.
     */
    private fun actualizarInterfaz(view: View) {
        val txtScore = view.findViewById<TextView>(R.id.txt_score)
        txtScore.text = score.toString()
    }

    /**
     * Actualiza el puntaje del usuario en Firebase Firestore si es mayor que el puntaje actual almacenado.
     */
    private fun actualizarPuntuacionEnFirebase(email: String) {
        if (email.isNotEmpty()) {
            val userRef = db.collection("users").document(email)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val currentScore = document.getLong("score")?.toInt() ?: 0
                        if (score > currentScore) {
                            userRef.update("score", score)
                                .addOnSuccessListener {
                                    Log.d("update_score", "Score updated successfully")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("error_update_score", "Error updating score", e)
                                }
                        }
                    } else {
                        Log.d("get_document", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("error_get_score", "Error getting current score", e)
                }
        } else {
            Log.w("update_score", "Email is empty, cannot update score")
        }
    }

    /**
     * Configura los eventos de clic para los botones en la vista.
     */
    private fun configurarEventos(view: View) {
        val btnVolverAJugar = view.findViewById<Button>(R.id.btn_restart_game)
        btnVolverAJugar.setOnClickListener {
            // Regresa al fragmento anterior
            view.findNavController().popBackStack()
        }

        val btnVolverAlMenu = view.findViewById<Button>(R.id.btn_menu)
        btnVolverAlMenu.setOnClickListener {
            // Regresa al fragmento del menú principal
            view.findNavController().popBackStack(R.id.menuGameFragment, false)
        }
    }

    /**
     * Sobrescribe el método onAttach para manejar el botón de retroceso del dispositivo.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Crea un callback para manejar el botón de retroceso del dispositivo
        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                // Muestra un mensaje Toast indicando al usuario que seleccione uno de los botones
                Toast.makeText(context, "No se puede regresar, seleccione uno de los botones", Toast.LENGTH_SHORT).show()
            }
        }
        // Agrega el callback al dispatcher de retroceso del fragmento
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }

}

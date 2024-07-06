package com.example.memocrazy

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.utils.KEY_SCORE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

/**
 * Se encarga de mostrar los mejores scores (TOP10 de los jugadores).
 * @autor Carlos Mijail Mamani Anccasi
 * @creacionFecha 19/06/24
 * @ultimaModificacion 06/07/24
 */
class ScoreFragment : Fragment(R.layout.fragment_score) {

    private var score by Delegates.notNull<Int>()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            score = it.getInt(KEY_SCORE, 0)
        }

        // Inicializa Firebase Firestore y FirebaseAuth
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Obtén el usuario actual
        val user = auth.currentUser
        if (user != null) {
            val email = user.email ?: ""
            // Actualiza la interfaz de usuario con la puntuación
            actualizarInterfaz(view)

            // Actualiza la puntuación en Firebase
            actualizarPuntuacionEnFirebase(email)
        } else {
            Log.w("auth_error", "No user is currently signed in")
        }

        // Configura los eventos de los botones
        configurarEventos(view)
    }

    private fun actualizarInterfaz(view: View) {
        val txtScore = view.findViewById<TextView>(R.id.txt_score)
        txtScore.text = score.toString()
    }

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

    private fun configurarEventos(view: View) {
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

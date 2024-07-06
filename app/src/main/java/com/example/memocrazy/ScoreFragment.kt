package com.example.memocrazy

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memocrazy.utils.KEY_SCORE
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

/**
 * @problemDescription Se encargara de mostrar los mejores scores(TOP10 de los jugadores)
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 06/07/24
 */
class ScoreFragment : Fragment(R.layout.fragment_score) {

    private var puntuacion by Delegates.notNull<Int>()
    private lateinit var email: String
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            puntuacion = it.getInt(KEY_SCORE, 0)
            email = it.getString("email", "")
        }

        db = FirebaseFirestore.getInstance()

        actualizarInterfaz(view)
        actualizarPuntuacionEnFirebase()
        eventos(view)
    }

    private fun actualizarInterfaz(view: View) {
        val txtScore = view.findViewById<TextView>(R.id.txt_score)
        txtScore.text = puntuacion.toString()
    }

    private fun actualizarPuntuacionEnFirebase() {
        val userRef = db.collection("users").document(email)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val currentScore = document.getLong("score")?.toInt() ?: 0
                    if (puntuacion > currentScore) {
                        userRef.update("score", puntuacion)
                            .addOnSuccessListener {
                                Log.d("update_score", "Score updated successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.w("error_update_score", "Error updating score", e)
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("error_get_score", "Error getting current score", e)
            }
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

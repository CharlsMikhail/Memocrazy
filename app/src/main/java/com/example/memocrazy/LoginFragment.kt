package com.example.memocrazy

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore


/**
 * @problemDescription Se encarga de la vista de login, donde el ususario sera el key para un jugador
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        eventos(view)
    }


    private fun validarEmailUser(userEmail: String): Boolean {
        return if (userEmail.isEmpty()) {
            txtEmail.error = "El campo email no puede estar vacío"
            false
        } else {
            true
        }
    }

    private fun validarPasswordUser(userPassword: String): Boolean {
        return if (userPassword.length < 6) {
            txtPassword.error = "La contraseña debe tener al menos 6 caracteres"
            false
        } else {
            true
        }
    }

    private fun eventos(view: View) {
        txtEmail = view.findViewById<EditText>(R.id.et_user_mail)
        txtPassword = view.findViewById<EditText>(R.id.et_user_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val btnRegister = view.findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if (validarEmailUser(email) && validarPasswordUser(password)) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showMenu(view)
                        } else {
                            val errorMessage = when (task.exception) {
                                is FirebaseAuthUserCollisionException ->
                                    "Ya existe un usuario con este correo electrónico"
                                else -> "Error al registrar el usuario"
                            }
                            showError(errorMessage)
                        }
                    }
            }
        }

        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            // Validar email y contraseña antes de iniciar sesión
            if (validarEmailUser(email) && validarPasswordUser(password)) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showMenu(view)
                        } else {
                            showError("Error al iniciar sesión")
                        }
                    }
            }
        }

    }

    private fun showError(message: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun showMenu(view: View) {
        val email = txtEmail.text.toString()
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf("correo" to email, "score" to 123)

        db.collection("users").document(email).set(user)
            .addOnSuccessListener { Log.d("insert", "DocumentSnapshot successfully written!")}
            .addOnFailureListener { e -> Log.w("error_insert", "Error writing document", e) }

        view.findNavController().navigate(R.id.action_loginFragment_to_menuGameFragment)
    }

    
}
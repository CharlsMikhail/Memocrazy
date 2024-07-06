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
 * Fragmento responsable de la vista de inicio de sesión y registro de usuarios.
 *
 * @property txtEmail Campo de texto para el correo electrónico del usuario.
 * @property txtPassword Campo de texto para la contraseña del usuario.
 * @property db Instancia de Firestore para operaciones de base de datos.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var db: FirebaseFirestore

    /**
     * Configura los eventos de clic para los botones de registro e inicio de sesión.
     *
     * @param view Vista raíz del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        eventos(view)
    }

    /**
     * Valida el formato del correo electrónico ingresado por el usuario.
     *
     * @param userEmail Correo electrónico ingresado por el usuario.
     * @return `true` si el correo electrónico es válido, `false` si es inválido.
     */
    private fun validarEmailUser(userEmail: String): Boolean {
        return if (userEmail.isEmpty()) {
            txtEmail.error = "El campo email no puede estar vacío"
            false
        } else {
            true
        }
    }

    /**
     * Valida la longitud de la contraseña ingresada por el usuario.
     *
     * @param userPassword Contraseña ingresada por el usuario.
     * @return `true` si la contraseña es válida, `false` si es inválida.
     */
    private fun validarPasswordUser(userPassword: String): Boolean {
        return if (userPassword.length < 6) {
            txtPassword.error = "La contraseña debe tener al menos 6 caracteres"
            false
        } else {
            true
        }
    }

    /**
     * Configura los eventos de clic para los botones de registro e inicio de sesión.
     *
     * @param view Vista raíz del fragmento.
     */
    private fun eventos(view: View) {
        txtEmail = view.findViewById(R.id.et_user_mail)
        txtPassword = view.findViewById(R.id.et_user_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val btnRegister = view.findViewById<Button>(R.id.btn_register)

        // Configurar evento clic para el botón de registro
        btnRegister.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            // Validar email y contraseña antes de intentar registrar al usuario
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

        // Configurar evento clic para el botón de inicio de sesión
        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            // Validar email y contraseña antes de intentar iniciar sesión
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

    /**
     * Muestra un diálogo de error con el mensaje especificado.
     *
     * @param message Mensaje de error a mostrar en el diálogo.
     */
    private fun showError(message: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * Guarda la información del usuario en Firestore y navega al menú principal del juego.
     *
     * @param view Vista raíz del fragmento.
     */
    private fun showMenu(view: View) {
        val email = txtEmail.text.toString()
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf("correo" to email, "score" to 123)

        // Guardar información del usuario en Firestore
        db.collection("users").document(email).set(user)
            .addOnSuccessListener { Log.d("insert", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("error_insert", "Error writing document", e) }

        // Navegar al menú principal del juego
        view.findNavController().navigate(R.id.action_loginFragment_to_menuGameFragment)
    }
}

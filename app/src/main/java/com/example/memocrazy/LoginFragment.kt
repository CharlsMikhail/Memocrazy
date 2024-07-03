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
import com.google.firebase.firestore.FirebaseFirestore


/**
 * @problemDescription Se encarga de la vista de login, donde el ususario sera el key para un jugador
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var txtEmail: EditText
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        eventos(view)
    }


    private fun validarPasswordUser(userPassword: String) {

    }

    private fun validarEmailUser(userEmail: String) {
        
    }

    private fun eventos(view: View) {
        txtEmail = view.findViewById<EditText>(R.id.et_user_mail)
        val txtPassword = view.findViewById<EditText>(R.id.et_user_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val btnRegister = view.findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {

            validarEmailUser(txtPassword.text.toString())

            validarPasswordUser(txtEmail.text.toString())


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                txtEmail.text.toString(), txtPassword.text.toString()
            ).addOnCompleteListener{
                if (it.isSuccessful){
                    showMenu(view)
                } else{
                    showError()
                }
            }
        }

        btnLogin.setOnClickListener {

            validarEmailUser(txtPassword.text.toString())

            validarPasswordUser(txtEmail.text.toString())


            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                txtEmail.text.toString(), txtPassword.text.toString()

            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    showMenu(view)
                } else {
                    showError()
                }
            }
        }


    }

    private fun showError() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
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
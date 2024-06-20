package com.example.memocrazy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventos(view)
    }

    private fun eventos(view: View) {
        val btnLogin = view.findViewById<Button>(R.id.btn_init)

        btnLogin.setOnClickListener {
            val userName = view.findViewById<EditText>(R.id.et_user)

            view.findNavController().navigate(R.id.action_loginFragment_to_menuGameFragment)

        }
    }
}
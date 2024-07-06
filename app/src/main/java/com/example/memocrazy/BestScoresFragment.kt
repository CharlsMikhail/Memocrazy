package com.example.memocrazy

import com.google.firebase.firestore.FirebaseFirestore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memocrazy.adapter.BestScoresAdapter
import com.example.memocrazy.entities.BestScore

/**
 * @problemDescription Se encargara de mostrar los mejores scores(TOP10 de los jugadores)
 * @author Carlos Mijail Mamani Anccasi
 * @creationDate 19/06/24
 * @lastModification 20/06/24
 */
class BestScoresFragment : Fragment(R.layout.fragment_best_scores) {

    private lateinit var userAdapter: BestScoresAdapter
    private lateinit var topScores: MutableList<BestScore>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView(view)

    }

    private fun initRecycleView(view: View) {
        val manager = LinearLayoutManager(context)
        userAdapter = BestScoresAdapter(mutableListOf()) // Consulta a base de datos
        val decoration = DividerItemDecoration(context, manager.orientation)
        val usersRecyler = view.findViewById<RecyclerView>(R.id.recycler_best_score)
        usersRecyler.layoutManager = manager
        usersRecyler.adapter = userAdapter
        usersRecyler.addItemDecoration(decoration)
    }

}